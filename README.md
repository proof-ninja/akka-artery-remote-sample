# Akka Artery Remote Sample

## How to use

```bash
$ sbt

sbt> compile

sbt> assembly

sbt> exit

$ java -jar ./target/scala-2.13/akka-artery-remote-sample-assembly-0.1.0.jar 25521

# On another console
$ java -Dconfig.file=src/main/resources/application2.conf -jar ./target/scala-2.13/akka-artery-remote-sample-assembly-0.1.0.jar 25520
```

And type your messages.