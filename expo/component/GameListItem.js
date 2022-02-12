import React from "react";

///////////////////////////////////////////////////////////////////////////////
import { Avatar, HStack, Pressable, Text } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
const GameListItem = (props) => {
  const { navigation, path, platform } = props;
  return (
    <Pressable
      height={10}
      margin={2}
      onPress={() => {
        if (util.isFolder({ path })) {
          navigation.push("GameList", {
            path,
            platform,
          });
        } else {
          navigation.navigate("Game", {
            path,
            platform,
          });
        }
      }}
    >
      <HStack alignItems="center">
        <Avatar
          bg="black"
          source={
            util.isFolder({ path })
              ? require("../assets/folder-icon.png")
              : null
          }
        />
        <Text color="white" fontSize="lg" marginLeft={4}>
          {util.isFolder({ path })
            ? util.getFolderName({ path: props.path })
            : util.getFileName({ path: props.path })}
        </Text>
      </HStack>
    </Pressable>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default GameListItem;
