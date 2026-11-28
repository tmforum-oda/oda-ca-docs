// Show the site in dark mode by default, to match the ODA Canvas training videos.
// The theme toggle still works, and Furo remembers the reader's choice.
// Furo loads this script after it has applied the stored theme, so on a first
// visit apply the default straight away as well.
try {
  if (!localStorage.getItem("theme")) {
    localStorage.setItem("theme", "dark");
    document.body.dataset.theme = "dark";
  }
} catch (e) {
  // Storage blocked: Furo falls back to the system setting.
}
