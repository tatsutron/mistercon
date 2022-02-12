import React from "react";

///////////////////////////////////////////////////////////////////////////////
import { Pressable, Text } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
const GameListItem = (props) => {
  const { console, navigation, path } = props;
  return (
    <Pressable
      margin={2}
      onPress={() => {
        if (util.isFolder({ path })) {
          navigation.push("Console", {
            console,
            path,
          });
        } else {
          navigation.navigate("Game", {
            console,
            path,
          });
        }
      }}
    >
      <Text fontSize="lg" color="white">
        {util.isFolder({ path })
          ? util.getFolderName({ path: props.path })
          : util.getFileName({ path: props.path })}
      </Text>
    </Pressable>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default GameListItem;
