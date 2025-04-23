import React from 'react';
import {
  ScrollView,
  Text,
  Button,
  View,
  Alert,
} from 'react-native';

function App(): React.JSX.Element {

  // JS error - normal throw
  function triggerJsCrash() {
    throw new Error("🔥 JS Crash: This is a manual error.");
  }

  // Stack overflow (recursive call)
  function triggerStackOverflow() {
    function recurse() {
      recurse(); // This will crash the JS engine due to stack overflow
    }
    recurse();
  }

  return (
    <ScrollView contentContainerStyle={{ flexGrow: 1, justifyContent: 'center', alignItems: 'center', padding: 16 }}>
      <Text style={{ fontSize: 20, fontWeight: 'bold', marginBottom: 20 }}>
        Crash Simulator 🧨
      </Text>

      <Button
        title="💥 JS Crash"
        onPress={triggerJsCrash}
      />

      <View style={{ height: 16 }} />

      <View style={{ height: 16 }} />

      <Button
        title="🔁 Stack Overflow"
        onPress={triggerStackOverflow}
      />

      <View style={{ marginTop: 30 }}>
        <Text style={{ fontSize: 14, color: '#666', textAlign: 'center' }}>
          This screen simulates different types of crash instances for testing purposes.
        </Text>
      </View>
    </ScrollView>
  );
}

export default App;
