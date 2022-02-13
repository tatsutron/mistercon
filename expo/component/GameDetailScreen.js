import React from "react";

///////////////////////////////////////////////////////////////////////////////
import {
  Center,
  Column,
  Divider,
  Image,
  ScrollView,
  Text,
  View,
} from "native-base";

///////////////////////////////////////////////////////////////////////////////
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
const GameDetailScreen = ({ navigation, route }) => {
  const { path, platform } = route.params;
  const [metadata, setMetadata] = React.useState(null);

  React.useEffect(() => {
    return navigation.addListener("focus", async () => {
      let sha1;
      try {
        const { host, port } = require("../config");
        const headerSize = platform.format.find((format) => {
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
        const { port, tatsudb } = require("../config");
        const url = `http://${tatsudb}:${port}/metadata/${sha1}`;
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
      <ScrollView
        _contentContainerStyle={{
          padding: "10",
        }}
        bg="black"
      >
        <Column space={5}>
          {metadata.publisher && (
            <Column space={1}>
              <Text color="white">Publisher</Text>
              <Divider />
              <Text color="#28a4ea" fontSize={"md"}>
                {metadata.publisher}
              </Text>
            </Column>
          )}
          {metadata.developer && (
            <Column space={1}>
              <Text color="white">Developer</Text>
              <Divider />
              <Text color="#28a4ea" fontSize={"md"}>
                {metadata.developer}
              </Text>
            </Column>
          )}
          {metadata.releaseDate && (
            <Column space={1}>
              <Text color="white">Release Date</Text>
              <Divider />
              <Text color="#28a4ea" fontSize={"md"}>
                {metadata.releaseDate}
              </Text>
            </Column>
          )}
          {metadata.region && (
            <Column space={1}>
              <Text color="white">Region</Text>
              <Divider />
              <Text color="#28a4ea" fontSize={"md"}>
                {metadata.region}
              </Text>
            </Column>
          )}
          {metadata.genre && (
            <Column space={1}>
              <Text color="white">Genre</Text>
              <Divider />
              <Text color="#28a4ea" fontSize={"md"}>
                {metadata.genre}
              </Text>
            </Column>
          )}
          {metadata.description && (
            <Column space={1}>
              <Text color="white">Description</Text>
              <Divider />
              <Text color="#28a4ea" fontSize={"md"}>
                {metadata.description}
              </Text>
            </Column>
          )}
          {metadata.frontCover && (
            <Column space={1}>
              <Text color="white">Front Cover</Text>
              <Divider />
              <Center>
                <Image
                  alt="Front Cover"
                  bg="black"
                  resizeMode="cover"
                  size={"2xl"}
                  source={{
                    uri: metadata.frontCover,
                  }}
                  style={{
                    resizeMode: "contain",
                  }}
                />
              </Center>
            </Column>
          )}
          {metadata.backCover && (
            <Column space={1}>
              <Text color="white">Back Cover</Text>
              <Divider />
              <Center>
                <Image
                  alt="Back Cover"
                  bg="black"
                  resizeMode="cover"
                  size={"2xl"}
                  source={{
                    uri: metadata.backCover,
                  }}
                  style={{
                    resizeMode: "contain",
                  }}
                />
              </Center>
            </Column>
          )}
          {metadata.cartridge && (
            <Column space={1}>
              <Text color="white">Cartridge</Text>
              <Divider />
              <Center>
                <Image
                  alt="Cartridge"
                  bg="black"
                  resizeMode="cover"
                  size={"2xl"}
                  source={{
                    uri: metadata.cartridge,
                  }}
                  style={{
                    resizeMode: "contain",
                  }}
                />
              </Center>
            </Column>
          )}
        </Column>
      </ScrollView>
    );
  } else {
    return <View style={{ flex: 1 }} bg="black" />;
  }
};

//////////////////////////////////////////////////////////////////////////////
export default GameDetailScreen;
