# Barcode Toolkit

- build

```sh
mvn clean install
```

- run cli

```sh
# encode mode
java -jar target/barcode-toolkit-<version>.jar --encode --text 'testing ...' --output 'out.jpg'


# decode mode
java -jar target/barcode-toolkit-<version>.jar --decode --input 'out.jpg'
```

- run GUI

```sh
java -jar target/barcode-toolkit-<version>.jar --gui
```

![Maven Package](https://github.com/minfaatong/barcode-toolkit/workflows/Maven%20Package/badge.svg)
![Java CI with Maven](https://github.com/avatar21/barcode-toolkit/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)
