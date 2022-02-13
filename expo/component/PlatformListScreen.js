import React from "react";

///////////////////////////////////////////////////////////////////////////////
import {
  Avatar,
  HStack,
  Pressable,
  ScrollView,
  Text,
  VStack,
} from "native-base";

///////////////////////////////////////////////////////////////////////////////
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
const PlatformListScreen = ({ navigation, route }) => {
  const { model, path } = route.params;
  const [platformList, setPlatformList] = React.useState([]);

  React.useEffect(() => {
    return navigation.addListener("focus", async () => {
      const { host, port } = require("../config");
      const url = `http://${host}:${port}/scan/rbf/${path}`;
      try {
        const response = await fetch(url);
        const entries = await response.json();
        const arr = [];
        Object.keys(model).forEach((key) => {
          const platform = model[key];
          const match = entries.find((path) => {
            const filename = util.getFileName({ path });
            return filename.startsWith(platform.core);
          });
          if (match) {
            arr.push(platform);
          }
        });
        setPlatformList(arr);
      } catch (error) {
        alert(error);
      }
    });
  }, [navigation]);

  return (
    <ScrollView bg="black">
      {platformList.map((platform, index) => {
        return (
          <VStack
            justifyContent="center"
            key={index}
            style={{
              height: 55,
            }}
          >
            <Pressable
              onPress={() => {
                navigation.navigate("GameList", {
                  path: `${require("../config").games}/${platform.folder}`,
                  platform,
                });
              }}
            >
              <HStack key={index} alignItems="center">
                <Avatar
                  bg="black"
                  borderWidth={1}
                  borderColor="white"
                  marginLeft={2}
                  source={platform.image}
                />
                <Text fontSize="lg" color="white" marginLeft={4}>
                  {platform.name}
                </Text>
              </HStack>
            </Pressable>
          </VStack>
        );
      })}
    </ScrollView>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default PlatformListScreen;
