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

[![Maven Package](https://github.com/minfaatong/barcode-toolkit/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/minfaatong/barcode-toolkit/actions/workflows/maven-publish.yml)
