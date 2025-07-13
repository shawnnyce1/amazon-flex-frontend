import React, { useState } from 'react';
import { View, StyleSheet } from 'react-native';
import { Text, Button, TextInput } from 'react-native-paper';
import { grabBlocks } from '../api/flexApi';

export default function DashboardScreen({ route, navigation }) {
  const { status, currentUrl } = route.params;
  const [loading, setLoading] = useState(false);
  const [blockStatus, setBlockStatus] = useState('');
  const [minRate, setMinRate] = useState('25');
  
  const handleGrabBlocks = async () => {
    setLoading(true);
    try {
      const response = await fetch(`http://192.168.1.221:8080/api/flex/blocks/filter`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ minRate: parseFloat(minRate) || 0 })
      });
      const data = await response.json();
      setBlockStatus(data.message || 'Request completed');
    } catch (error) {
      setBlockStatus('Error: ' + error.message);
    }
    setLoading(false);
  };

  return (
    <View style={styles.container}>
      <Text variant="headlineMedium" style={styles.title}>Login {status === 'success' ? 'Successful' : 'Failed'}</Text>
      <Text style={styles.text}>URL: {currentUrl}</Text>

      <TextInput
        label="Minimum $/hour"
        value={minRate}
        onChangeText={setMinRate}
        keyboardType="numeric"
        style={{ marginTop: 20 }}
      />
      
      {blockStatus ? <Text style={styles.blockStatus}>{blockStatus}</Text> : null}
      
      <Button 
        mode="contained" 
        onPress={handleGrabBlocks} 
        loading={loading}
        style={{ marginTop: 20 }}
      >
        Grab Blocks
      </Button>
      
      <Button mode="outlined" onPress={() => navigation.goBack()} style={{ marginTop: 10 }}>
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
  },
  blockStatus: {
    textAlign: 'center',
    marginTop: 20,
    fontWeight: 'bold'
  }
});
