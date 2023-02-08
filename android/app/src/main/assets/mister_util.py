import hashlib, os, sys


if sys.argv[1] == "list":
    path, extensions = sys.argv[2:]
    entries = []
    with os.scandir(path) as it:
        for entry in it:
            root, extension = os.path.splitext(entry.path)
            if os.path.basename(root).startswith("."):
                pass
            elif entry.is_dir():
                entries.append(os.path.join(entry.path, ""))
            else:
                if extension[1:] in extensions.split("|"):
                    entries.append(entry.path)
    print(";".join(entries))


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
    print(format(sha1.hexdigest()))
