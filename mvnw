#!/usr/bin/env sh
set -eu
VERSION=3.9.11
CACHE="$HOME/.m2/wrapper/dists/apache-maven-$VERSION"
HOME_MAVEN="$CACHE/apache-maven-$VERSION"
if [ ! -x "$HOME_MAVEN/bin/mvn" ]; then
  mkdir -p "$CACHE"
  ARCHIVE="$CACHE/maven.zip"
  curl -fL "https://archive.apache.org/dist/maven/maven-3/$VERSION/binaries/apache-maven-$VERSION-bin.zip" -o "$ARCHIVE"
  unzip -oq "$ARCHIVE" -d "$CACHE"
  rm -f "$ARCHIVE"
fi
exec "$HOME_MAVEN/bin/mvn" "$@"
