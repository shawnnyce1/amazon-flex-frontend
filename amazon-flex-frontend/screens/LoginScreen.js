import React, { useState } from 'react';
import { View, StyleSheet, Alert } from 'react-native';
import { TextInput, Button, Text, ActivityIndicator } from 'react-native-paper';
import { loginToAmazonFlex } from '../api/flexApi';

export default function LoginScreen({ navigation }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  const handleLogin = async () => {
    if (!email || !password) {
      Alert.alert('Error', 'Email and password are required.');
      return;
    }

    setLoading(true);
    const result = await loginToAmazonFlex(email, password);
    setLoading(false);

    if (result.success) {
      navigation.navigate('Dashboard', {
        status: result.data.status,
        currentUrl: result.data.currentUrl,
      });
    } else {
      Alert.alert('Login Failed', result.error);
    }
  };

  return (
    <View style={styles.container}>
      <Text variant="headlineMedium" style={styles.title}>Amazon Flex Login</Text>

      <TextInput
        label="Email"
        value={email}
        mode="outlined"
        onChangeText={setEmail}
        style={styles.input}
      />
      <TextInput
        label="Password"
        value={password}
        mode="outlined"
        secureTextEntry
        onChangeText={setPassword}
        style={styles.input}
      />

      <Button mode="contained" onPress={handleLogin} loading={loading} style={styles.button}>
        Login
      </Button>

      {loading && <ActivityIndicator style={{ marginTop: 20 }} />}
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
    textAlign: 'center',
    marginBottom: 20
  },
  input: {
    marginBottom: 10
  },
  button: {
    marginTop: 10
  }
});
