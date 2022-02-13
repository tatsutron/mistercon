import React from "react";

///////////////////////////////////////////////////////////////////////////////
import { Avatar, HStack, Pressable, Text, VStack } from "native-base";

///////////////////////////////////////////////////////////////////////////////
const PlatformListItem = (props) => {
  const { navigation, platform } = props;
  return (
    <VStack
      justifyContent="center"
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
        <HStack alignItems="center">
          <Avatar
            bg="black"
            borderWidth={1}
            borderColor="white"
            marginLeft={2}
            source={platform.image}
          />
          <Text color="white" fontSize="lg" marginLeft={4}>
            {platform.name}
          </Text>
        </HStack>
      </Pressable>
    </VStack>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default PlatformListItem;
