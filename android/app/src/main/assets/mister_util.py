import hashlib, os, sys


def scan(path, extensions, entries, recurse):
    with os.scandir(path) as it:
        for entry in it:
            root, extension = os.path.splitext(entry.path)
            if os.path.basename(root).startswith("."):
                pass
            elif entry.is_dir():
                if recurse:
                    scan(entry, entries)
            else:
                if extension[1:] in extensions.split("|"):
                    entries.append(entry.path)


if sys.argv[1] == "list":
    entries = []
    argc = len(sys.argv)
    if argc == 5 and sys.argv[2] == "-r":
        path, extensions = sys.argv[3:]
        scan(path, extensions, entries, recurse=True)
    elif argc == 4:
        path, extensions = sys.argv[2:]
        scan(path, extensions, entries, recurse=False)
    print(";".join(entries), end="")


if sys.argv[1] == "hash":
    path = sys.argv[2]
    buffer_size = 65536
    sha1 = hashlib.sha1()
    with open(path, "rb") as file:
        while True:
            data = file.read(buffer_size)
            if not data:
                break
            sha1.update(data)
    print(format(sha1.hexdigest()), end="")
