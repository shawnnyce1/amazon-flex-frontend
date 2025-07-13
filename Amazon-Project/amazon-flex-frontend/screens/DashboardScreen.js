import React from 'react';
import { View, StyleSheet } from 'react-native';
import { Text, Button } from 'react-native-paper';

export default function DashboardScreen({ route, navigation }) {
  const { status, currentUrl } = route.params;

  return (
    <View style={styles.container}>
      <Text variant="headlineMedium" style={styles.title}>Login {status === 'success' ? 'Successful' : 'Failed'}</Text>
      <Text style={styles.text}>URL: {currentUrl}</Text>

      <Button mode="outlined" onPress={() => navigation.goBack()} style={{ marginTop: 20 }}>
        Back to Login
      </Button>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    padding: 20
  },
  title: {
    marginBottom: 10,
    textAlign: 'center'
  },
  text: {
    textAlign: 'center'
  }
});
