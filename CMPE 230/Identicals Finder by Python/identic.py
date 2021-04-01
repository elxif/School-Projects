# Elif Nur Akalin
# 2018400312

import argparse, re, hashlib, os
hashes = {} # This is the main dictionary that my program holds hashes as keys and a pair
            # as value consisting of the path and the size of the object

# This method takes an absolute path and reads the contents and returns the hash of the file
def hashf(path):
    hasher = hashlib.sha256()
    fil = open(path, 'rb')
    content = fil.read()
    hasher.update(content)
    return hasher.hexdigest()

# This method searches for files recursively. For all files, if only -n is given as argument,
# it adds to the dictionary only the name as key and path and size as values.
# If no argument is given, it pretends like -c argument is given. It hashes every file, and adds
# them to the dictionary. If the hash exists in the dictionary, it appends the values to the existing 
# values of that hash.
# If both -c and -n are given arguments, the filename is hashed and concatenated with the hash, and 
# hashed again.

def searchfiles(path):
    for root, directories, filenames in os.walk(path):
        for filename in filenames:
            pathf = os.path.join(root, filename)
            size = os.path.getsize(pathf)
            if args.n and not args.c:
                if filename in hashes:
                    if (pathf, size) not in hashes[filename]:
                        hashes[filename].append((pathf, size))
                else:
                    hashes[filename] = [(pathf, size)]
            else:
                filehash = hashf(pathf)
                if not args.n:
                    if filehash in hashes:
                        if (pathf, size) not in hashes[filehash]:
                            hashes[filehash].append((pathf, size))
                    else:
                        hashes[filehash] = [(pathf,size)]
                else:
                    filehash2 = hashlib.sha256(filename.encode('utf-8')).hexdigest() + filehash
                    filehash3 = hashlib.sha256(filehash2.encode('utf-8')).hexdigest()
                    if filehash3 in hashes:
                        if (pathf, size) not in hashes[filehash3]:
                            hashes[filehash3].append((pathf, size))
                    else:
                        hashes[filehash3] = [(pathf,size)]
    return hashes

# This method works similarly to searchfiles method. The difference is I do the recursion myself.
# First I list all the files and hash them. I add them to a list called dirhashes. It is the
# dictionary for holding the hash values of the files and folders immediately inside that folder.
# After that, for every directory inside the current one, I call searchdirs again and create a
# recursion. This way, until we are in a leaf directory, the method is called. Then, all the hashes
# in the dirhashes are concatenated together and this string is hashed again. This hash is then added
# to hashes dictionary.
def searchdirs(path):
    dirhashes = []
    dirs = [f for f in os.listdir(path) if os.path.isdir(os.path.join(path, f))]
    files = [f for f in os.listdir(path) if os.path.isfile(os.path.join(path, f))]
    for f in files:
        pathf = os.path.join(path, f)
        filehash = hashf(pathf)
        if args.n:
            filehash2 = hashlib.sha256(f.encode('utf-8')).hexdigest() + filehash
            filehash = hashlib.sha256(filehash2.encode('utf-8')).hexdigest()
        dirhashes.append(filehash)
    for d in dirs:
        pathd = os.path.join(path, d)
        insidehash = searchdirs(pathd)
        if args.n:
            insidehash2 = hashlib.sha256(d.encode('utf-8')).hexdigest() + insidehash
            insidehash = hashlib.sha256(insidehash2.encode('utf-8')).hexdigest()
        dirhashes.append(insidehash)
    dirhashstr = ""
    dirhashes.sort()
    for s in dirhashes:
        dirhashstr += s
    dirhash = hashlib.sha256(dirhashstr.encode('utf-8')).hexdigest()
    size = dirsize(path)
    if args.n:
        dirname = os.path.basename(path)
        dirhash2 = hashlib.sha256(dirname.encode('utf-8')).hexdigest() + dirhash
        dirhash3 = hashlib.sha256(dirhash2.encode('utf-8')).hexdigest()
        if dirhash3 in hashes:
            if (path,size) not in hashes[dirhash3]:
                hashes[dirhash3].append((path,size))
        else:
            hashes[dirhash3] = [(path,size)]
    else:
        if dirhash in hashes:
            if (path,size) not in hashes[dirhash]:
                hashes[dirhash].append((path,size))
        else:
            hashes[dirhash] = [(path,size)]
    return dirhash

# This method is called only when directories are searched for identical names.
# The loop calls recursively and checks the name of every directory, hashes them,
# adds them to the dictionary of hashes.
def searchdirsname(path):
    for root, directories, filenames in os.walk(path):
        for directory in directories: 
            pathd = os.path.join(root, directory)
            size = os.path.getsize(pathd)
            if directory in hashes:
                if (pathd, size) not in hashes[directory]:
                    hashes[directory].append((pathd, size))
            else:
                hashes[directory] = [(pathd, size)]

# This method is for sorting the files and directories according to their sizes.
# Since I keep sizes of every directories and files as the second item in a pair
# I need to pair them accordingly.
def sorttuple(tup):  
    tup.sort(key = lambda x: x[1])  
    return tup

# After the recursive searching, this method is called. First it sorts the items
# having the same hash alphabetically, then if -s argument is given they are sorted
# according to their sizes. Then it sorts the list of paths alphabetically.
# It prints them accordingly, whether -s argument is given or not.
def output():
    results = []
    for i in hashes:
        if(len(hashes[i]) > 1):
            hashes[i].sort()
            if args.s and not (args.n and not args.c):
                sorttuple(hashes[i])
            results.append(hashes[i])

    results.sort()
    for result in results:
        for r in result:
            if args.s and not (args.n and not args.c):
                print(r[0],"\t",r[1])
            else:
                print(r[0])
        print()

# The main method for searching items, file or directory.
def run(args):
    paths = args.paths
    if args.d and args.n and not args.c:
        for path in paths:
            searchdirsname(os.path.abspath(path))
    elif args.d:
        for path in paths:
            searchdirs(os.path.abspath(path))
    else:
        for path in paths:
            searchfiles(os.path.abspath(path))
    output()

# A simple method that finds the size of every folder. It adds together
# all the files in that directory.
def dirsize(path):
    size = 0
    for root, directories, filenames in os.walk(path):
        for filename in filenames:
            size += os.path.getsize(os.path.join(root, filename))
    return size



# I didn't put the argument parser in a method because I wanted to access the
# arguments from anywhere.
parser = argparse.ArgumentParser(description="Search for identical files or directories.")
group1 = parser.add_mutually_exclusive_group()
group1.add_argument('-f',action='store_true',help="Search for identical files")
group1.add_argument('-d',action='store_true',help="Search for identical directories")
parser.add_argument("-c",action='store_true',help="Find items having identical contents. Default when not chosen.")
parser.add_argument("-n",action='store_true',help="Find items having identical names.")
parser.add_argument("-s",action='store_true',help="Print items with their sizes.")
parser.add_argument("paths", nargs='*', default=[os.getcwd()], help="Search for identicals in specific folders. Default is current working directory.")
parser.set_defaults(func=run)
args=parser.parse_args()
args.func(args)