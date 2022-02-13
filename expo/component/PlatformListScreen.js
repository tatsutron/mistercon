import React from "react";

///////////////////////////////////////////////////////////////////////////////
import { ScrollView } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import util from "../util/util";

///////////////////////////////////////////////////////////////////////////////
import PlatformListItem from "./PlatformListItem";

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
          <PlatformListItem
            key={index}
            navigation={navigation}
            platform={platform}
          />
        );
      })}
    </ScrollView>
  );
};

//////////////////////////////////////////////////////////////////////////////
export default PlatformListScreen;
