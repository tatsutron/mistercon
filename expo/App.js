import { Button, NativeBaseProvider, StatusBar, View } from "native-base";

///////////////////////////////////////////////////////////////////////////////
import { SafeAreaProvider } from "react-native-safe-area-context";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

///////////////////////////////////////////////////////////////////////////////
import util from "./util/util";

///////////////////////////////////////////////////////////////////////////////
import ConsoleScreen from "./component/ConsoleScreen";
import GameScreen from "./component/GameScreen";
import HomeScreen from "./component/HomeScreen";

///////////////////////////////////////////////////////////////////////////////
const Stack = createNativeStackNavigator();

///////////////////////////////////////////////////////////////////////////////
const App = () => {
  return (
    <SafeAreaProvider>
      <NativeBaseProvider>
        <View backgroundColor="#000" style={{ flex: 1 }}>
          <NavigationContainer>
            <StatusBar hidden />
            <Stack.Navigator
              initialRouteName="Home"
              mode="modal"
              screenOptions={{
                animationEnabled: false,
                headerStyle: {
                  backgroundColor: "#171717",
                },
                headerTintColor: "white",
              }}
            >
              <Stack.Screen
                name="Home"
                component={HomeScreen}
                options={{
                  headerShown: false,
                  title: "Consoles",
                }}
              />
              <Stack.Screen
                name="Console"
                component={ConsoleScreen}
                options={({ route }) => {
                  const { console } = route.params;
                  return {
                    title: console.name,
                  };
                }}
              />
              <Stack.Screen
                name="Game"
                component={GameScreen}
                options={({ route }) => {
                  const { console, path } = route.params;
                  return {
                    title: util.getFilename({ path }),
                    headerRight: () => (
                      <Button
                        onPress={() => {
                          util.loadGame({ console, path });
                        }}
                        variant="outline"
                      >
                        Play
                      </Button>
                    ),
                  };
                }}
              />
            </Stack.Navigator>
          </NavigationContainer>
        </View>
      </NativeBaseProvider>
    </SafeAreaProvider>
  );
};

///////////////////////////////////////////////////////////////////////////////
export default App;
