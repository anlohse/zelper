# Zelper - The Zip Helper

Zelper is a library for helping you to work with folders, zip files and even folders in memory. It provides some useful and very easy classes for working with zip folders.

 
## Examples

### How to compress a folder to a zip file

```java
FolderFileBundle theFolder = new FolderFileBundle("src");
ZipOutputHelper zipFile = new ZipOutputHelper("temp.zip");
zipFile.compress(theFolder);
```
### How to compress a memory folder to a zip file

```java
MemoryFileBundle mft = new MemoryFileBundle();
mft.put("src/test.txt", "Test file.".getBytes());
mft.put("src/test2.txt", "Second test file.".getBytes());
ZipOutputHelper zoh = new ZipOutputHelper("temp.zip");
zoh.compress(mft);
```

### How to extract a zip file to a folder

```java
ZipInputHelper zih = new ZipInputHelper("temp.zip");
zih.extractToFolder(new File("."));
```
