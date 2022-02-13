import React from "react";

///////////////////////////////////////////////////////////////////////////////
import { Avatar, Column, Pressable, Row, Text } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
const GameListItem = (props) => {
  const { navigation, path, platform } = props;
  return (
    <Column
      justifyContent="center"
      style={{
        height: 55,
      }}
    >
      <Pressable
        onPress={() => {
          if (util.isFolder({ path })) {
            navigation.push("GameList", { path, platform });
          } else {
            navigation.navigate("GameDetail", { path, platform });
          }
        }}
      >
        <Row alignItems="center">
          <Avatar
            bg="black"
            marginLeft={2}
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
        </Row>
      </Pressable>
    </Column>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default GameListItem;
