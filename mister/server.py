import json, os, subprocess
from bottle import route, run


@route("/list/<formats>/<path:path>")
def list(formats, path):
    return subprocess.run(
        ["/media/fat/mistercon/list", path, "({})$".format(formats)],
        stderr=subprocess.PIPE,
        stdout=subprocess.PIPE,
    ).stdout


@route("/hash/<header_size>/<path:path>")
def hash(header_size, path):
    return subprocess.run(
        ["/media/fat/mistercon/hash", path, "{}".format(header_size)],
        stderr=subprocess.PIPE,
        stdout=subprocess.PIPE,
    ).stdout


@route("/load/<command>/<path:path>")
def load(command, path):
    return subprocess.run(
        ["/media/fat/mistercon/mbc", "load_rom", command, path],
        stderr=subprocess.PIPE,
	    stdout=subprocess.PIPE,
    ).stdout


@route("/scan/<extensions>/<path:path>")
def scan(extensions, path):
    entries = []
    with os.scandir(path) as it:
        for entry in it:
            if entry.is_dir():
                entries.append(os.path.join(entry.path, ""))
            else:
                name, extension = os.path.splitext(entry.path)
                if extension[1:] in extensions.split("|"):
                    entries.append(entry.path)
    return json.dumps(entries)


run(host='0.0.0.0', port=8080, debug=True)
