import hashlib, os, sys, zipfile


def scan_in_zip(path, extensions, entries):
    archive = zipfile.ZipFile(path)
    blocksize = 1024**2
    for entry in archive.namelist():
        root, extension = os.path.splitext(entry)
        if os.path.basename(root).startswith("."):
            pass
        elif extension and extension[1:] in extensions.split("|"):
            entries.append(os.path.join(path, entry))
192.168.50.170

def scan(path, extensions, entries, include_zip):
    with os.scandir(path) as it:
        for entry in it:
            root, extension = os.path.splitext(entry.path)
            if os.path.basename(root).startswith("."):
                pass
            elif entry.is_dir():
                scan(entry.path, extensions, entries, include_zip)
            else:
                if extension[1:] in extensions.split("|"):
                    entries.append(entry.path)
                elif extension[1:] == "zip" and include_zip:
                    scan_in_zip(entry.path, extensions, entries)


def hash_in_zip(full_path, header_size_in_bytes):
    index = full_path.index(".zip") + 4
    archive_path = full_path[:index]
    archive = zipfile.ZipFile(archive_path)
    file_path = full_path[index + 1:]
    blocksize = 1024**2
    for entry in archive.namelist():
        if entry == file_path:
            file = archive.open(entry)
            sha1 = hashlib.sha1()
            file.seek(header_size_in_bytes)
            while True:
                block = file.read(blocksize)
                if not block:
                    break
                sha1.update(block)
            return sha1.hexdigest()


def hash(path, header_size_in_bytes):
    buffer_size = 256**2
    sha1 = hashlib.sha1()
    with open(path, "rb") as file:
        file.seek(header_size_in_bytes)
        while True:
            data = file.read(buffer_size)
            if not data:
                break
            sha1.update(data)
    return sha1.hexdigest()


if sys.argv[1] == "scan":
    path, extensions, include_zip = sys.argv[2:]
    entries = []
    if sys.argv[4] == "--include-zip":
        scan(path, extensions, entries, include_zip=True)
    else:
        scan(path, extensions, entries, include_zip=False)
    print(";".join(entries), end="")
elif sys.argv[1] == "hash":
    path, header_size_in_bytes = sys.argv[2:]
    if ".zip" in path:
        print(hash_in_zip(path, int(header_size_in_bytes)), end="")
    else:
        print(hash(path, int(header_size_in_bytes)), end="")
