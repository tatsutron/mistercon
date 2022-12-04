import os, sys

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
