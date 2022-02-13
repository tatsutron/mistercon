import React from "react";

///////////////////////////////////////////////////////////////////////////////
import { Avatar, Column, Pressable, Row, Text } from "native-base";

///////////////////////////////////////////////////////////////////////////////
const PlatformListItem = (props) => {
  const { navigation, platform } = props;
  return (
    <Column
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
        <Row alignItems="center">
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
        </Row>
      </Pressable>
    </Column>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default PlatformListItem;
