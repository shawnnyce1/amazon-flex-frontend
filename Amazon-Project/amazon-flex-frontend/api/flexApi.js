import axios from 'axios';

const API_BASE_URL = 'http://192.168.1.221:8080/api/flex';

export const loginToAmazonFlex = async (email, password) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/login`, {
      email,
      password
    });
    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, error: error.response?.data?.message || error.message };
  }
};
