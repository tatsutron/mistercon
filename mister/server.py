import subprocess
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


run(host='0.0.0.0', port=8080, debug=True)
