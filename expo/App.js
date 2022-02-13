import "react-native-gesture-handler"; // Must go first

///////////////////////////////////////////////////////////////////////////////
import { NativeBaseProvider, StatusBar } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import { SafeAreaProvider } from "react-native-safe-area-context";
import { DarkTheme, NavigationContainer } from "@react-navigation/native";

///////////////////////////////////////////////////////////////////////////////
import DrawerNavigator from "./navigator/DrawerNavigator";

///////////////////////////////////////////////////////////////////////////////
const App = () => {
  return (
    <SafeAreaProvider>
      <NativeBaseProvider>
        <NavigationContainer theme={DarkTheme}>
          <StatusBar hidden />
          <DrawerNavigator />
        </NavigationContainer>
      </NativeBaseProvider>
    </SafeAreaProvider>
  );
};

///////////////////////////////////////////////////////////////////////////////
export default App;
