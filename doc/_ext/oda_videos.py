"""ODA Canvas training videos on the documentation site.

Reads doc/videos.yaml and:

* writes the Training videos pages (docs/training-videos/): an overview with a
  card per video, and a page per video with the player, chapters, related
  documentation and the transcript;
* adds a "Watch" box under the title of each page listed in a video's "related"
  list, unless that page already embeds the video.
"""

import html
import os
from datetime import date

import yaml

OUT_DIR = "docs/training-videos"  # relative to the Sphinx source folder

_videos = []


def _load(app):
    global _videos
    path = os.path.join(app.srcdir, "videos.yaml")
    with open(path, encoding="utf-8") as f:
        _videos = (yaml.safe_load(f) or {}).get("videos", [])
    _videos.sort(key=lambda v: v["part"])


def _page_name(video):
    return f"part-{video['part']}"


def _thumbnail(video):
    return f"https://i.ytimg.com/vi/{video['id']}/hqdefault.jpg"


def _long_date(iso):
    d = date.fromisoformat(str(iso))
    return f"{d.day} {d.strftime('%B %Y')}"


def _article(duration):
    """'An' before a length read aloud with a vowel sound (8, 11, 18, 80...), else 'A'."""
    minutes = duration.split(":")[0]
    return "An" if minutes.startswith("8") or minutes in ("11", "18") else "A"


def _e(text):
    return html.escape(str(text), quote=True)


def _overview():
    cards = []
    for v in _videos:
        cards.append(
            f'<a class="oda-video-card" href="{_page_name(v)}.html">'
            f'<img src="{_thumbnail(v)}" alt="" loading="lazy">'
            f'<span class="oda-card-kicker">Part {v["part"]} · {v["duration"]}</span>'
            f'<span class="oda-card-title">{_e(v["title"])}</span>'
            f'<span class="oda-card-text">{_e(v["summary"])}</span>'
            f"</a>"
        )
    toctree = "\n".join(_page_name(v) for v in _videos)
    return f"""# Training videos

The ODA Canvas training series explains the Canvas and how to build ODA Components that run on
it. Each video has chapters, a full transcript, and links to the documentation it covers. The
videos are on the [Inside Telco Architecture](https://www.youtube.com/@insidetelcoarchitecture) YouTube
channel, with captions.

<div class="oda-video-cards">
{"".join(cards)}
</div>

More parts are in production, including carbon-aware software. They will appear here when they
are published.

```{{toctree}}
:hidden:

{toctree}
```
"""


def _related_list(video):
    return "\n".join(f"- {{doc}}`/{docname}`" for docname in video.get("related", []))


def _video_page(video):
    vid = video["id"]
    paragraphs = "\n\n".join([video["summary"], *video.get("description", [])])
    chapters = "\n".join(
        f"- [{c['time']}](https://youtu.be/{vid}?t={c['seconds']}) {c['title']}"
        for c in video["chapters"]
    )
    transcript = "\n\n".join(
        f"**[{c['time']}](https://youtu.be/{vid}?t={c['seconds']}) {c['title']}**\n\n{c['transcript']}"
        for c in video["chapters"]
    )
    others = [v for v in _videos if v is not video]
    nxt = next((v for v in _videos if v["part"] > video["part"]), None)
    next_line = (
        f"Next in the series: [Part {nxt['part']}, {nxt['title']}]({_page_name(nxt)}.md)."
        if nxt
        else "This is the latest video in the series."
    )
    return f"""# {video['title']}

<p class="oda-video-meta">Part {video['part']} · {video['duration']} · Published {_long_date(video['published'])}</p>

<div class="oda-video-player">
<iframe src="https://www.youtube-nocookie.com/embed/{vid}" title="{_e(video['title'])}" loading="lazy"
  allow="accelerometer; encrypted-media; gyroscope; picture-in-picture; fullscreen" allowfullscreen></iframe>
</div>

{paragraphs}

[Watch on YouTube](https://youtu.be/{vid}). {next_line}

## Related documentation

{_related_list(video)}

## Chapters

{chapters}

## Transcript

The narration of the video, chapter by chapter. The captions on YouTube use the same text.

<details class="oda-transcript">
<summary>Show the transcript</summary>

{transcript}

</details>
"""


def _write_pages(app):
    _load(app)
    out = os.path.join(app.srcdir, OUT_DIR)
    os.makedirs(out, exist_ok=True)
    with open(os.path.join(out, "index.md"), "w", encoding="utf-8") as f:
        f.write(_overview())
    for v in _videos:
        with open(os.path.join(out, _page_name(v) + ".md"), "w", encoding="utf-8") as f:
            f.write(_video_page(v))


def _watch_box(app, docname, source):
    """Add a 'Watch' box under the first heading of each related page."""
    if not docname.startswith(("canvas/", "docs/")) or docname.startswith(OUT_DIR):
        return
    if not str(app.env.doc2path(docname)).endswith(".md"):
        return
    boxes = []
    for v in _videos:
        if docname in v.get("related", []) and v["id"] not in source[0]:
            target = os.path.relpath(
                f"{OUT_DIR}/{_page_name(v)}.md", os.path.dirname(docname)
            ).replace(os.sep, "/")
            boxes.append(
                f"```{{admonition}} Watch: Part {v['part']}, {v['title']}\n"
                f":class: oda-watch\n\n"
                f"{_article(v['duration'])} {v['duration']} video in the ODA Canvas training series. "
                f"[Watch it with chapters and a transcript]({target}), "
                f"or [on YouTube](https://youtu.be/{v['id']}).\n"
                f"```\n"
            )
    if not boxes:
        return
    lines = source[0].split("\n")
    in_fence = False
    for i, line in enumerate(lines):
        if line.lstrip().startswith(("```", "~~~")):
            in_fence = not in_fence
        if not in_fence and line.startswith("# "):
            lines[i + 1 : i + 1] = [""] + boxes
            source[0] = "\n".join(lines)
            return
    source[0] = "\n".join(boxes) + "\n" + source[0]


def setup(app):
    app.connect("builder-inited", _write_pages)
    app.connect("source-read", _watch_box)
    return {"parallel_read_safe": True, "parallel_write_safe": True}
