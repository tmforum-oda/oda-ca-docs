# CTK Troubleshooting Guide

A Conformance Test Kit (CTK) checks a component against the ODA Component standard: the component's YAML definition is compared with the published component specification, and the running component is exercised through its APIs on an ODA Canvas.

## Which CTK to use

Use the per-component conformance test kits in [tmforum-rand/TMForum-ODA-Ready-for-publication](https://github.com/tmforum-rand/TMForum-ODA-Ready-for-publication). Each published component has one, under `<TMFCnnn-Name>/CTK/componentCTK`. The kit:

- checks the deployed component manifest against the published TMFC specification (`oda.tmforum.org/v1`)
- checks that the component deployed successfully on the canvas, and checks its security API
- downloads and runs the official TM Forum Open API CTK for each mandatory API against the component's live API URLs
- writes an HTML report to `componentCTK/Reports/<TMFCnnn-Name>/reports/index.html`

Follow the README in the `componentCTK` folder to configure `CHANGE_ME.json` (Helm release name, component ID, namespace, and product details) and run `scripts/CTK_Executor.py`.

The older [oda-component-ctk](https://github.com/tmforum-oda/oda-component-ctk) (L1/L2 static and dynamic tests) only supports component specifications up to `v1beta1` and can't assess components built to the current `v1` specification ([oda-component-ctk#45](https://github.com/tmforum-oda/oda-component-ctk/issues/45)).

## Before you run it

- The component must be deployed on the canvas and show `Complete` (`kubectl get components -n components`). Components it depends on must be deployed first.
- `helm`, `kubectl` (connected to the canvas), Python 3.10+, Node.js 16+ and Docker must be available. The Open API CTKs run in Docker containers.
- The kit needs internet access to download the published specification and the Open API CTKs.

## Common problems

**Every API test fails with `ECONNREFUSED`.** The Open API CTKs run in Docker and call the API URLs the canvas publishes (`kubectl get exposedapis -n components`). If those URLs use `127.0.0.1` or `localhost`, the container calls itself. Give the Istio ingress an address that the containers can reach (`host.docker.internal` on Docker Desktop, the `docker0` address on Linux) before installing the component; see the "Local clusters (kind)" section of the [canvas installation guide](https://github.com/tmforum-oda/oda-canvas/blob/main/installation/README.md).

**The component never reaches `Complete`, and its APIs have no URL.** The Istio ingress has no external address (common on kind or other local clusters). See the same installation guide section.

**Windows: `UnicodeEncodeError: 'charmap' codec can't encode character`.** The script prints emoji. Set `PYTHONUTF8=1` before running it.

**Windows: `[WinError 3] The system cannot find the path specified` while preparing an API CTK.** The path is too long. Run the kit from a short folder such as `C:\ctk`, or enable Windows long paths.

**Windows: `'run.bat' is not recognized as an internal or external command`.** The environment variable `NoDefaultCurrentDirectoryInExePath` is set, which stops `cmd` running programs from the current folder. Run the kit from a terminal where it isn't set.

**Windows: dependency install fails in `WINDOWS-RUN.bat`.** The script runs `pip install requirements.txt` without `-r`. Run `pip install -r scripts/requirements.txt` yourself, then `python CTK_Executor.py` from the `scripts` folder.
