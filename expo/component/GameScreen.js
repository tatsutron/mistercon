import React from "react";
import { Image } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
const config = require("../config.json");

///////////////////////////////////////////////////////////////////////////////
const GameScreen = ({ navigation, route }) => {
  const { console, path } = route.params;
  const [metadata, setMetadata] = React.useState(null);

  React.useEffect(() => {
    return navigation.addListener("focus", async () => {
      let sha1;
      try {
        const host = config.host;
        const port = config.port;
        const headerSize = console.format.find((format) => {
          return format.extension === util.getExtension({ path });
        }).headerSizeInBytes;
        const url = `http://${host}:${port}/hash/${headerSize}/${path}`;
        const response = await fetch(url);
        const text = await response.text();
        sha1 = text.trim();
      } catch (error) {
        alert(error);
      }
      try {
        const host = "54.84.164.224";
        const port = "8080";
        const url = `http://${host}:${port}/metadata/${sha1}`;
        const response = await fetch(url);
        const json = await response.json();
        setMetadata(json);
      } catch (error) {
        alert(error);
      }
    });
  }, [navigation]);

  if (metadata !== null) {
    return (
      <Image
        alt="Front cover"
        source={{
          uri: metadata.frontCover,
        }}
        style={{
          width: "100%",
          height: "100%",
          resizeMode: "contain",
        }}
      />
    );
  } else {
    return null;
  }
};

//////////////////////////////////////////////////////////////////////////////
export default GameScreen;
