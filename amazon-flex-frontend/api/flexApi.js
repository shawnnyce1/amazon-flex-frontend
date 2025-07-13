

const API_BASE_URL = 'http://192.168.1.221:8080/api/flex';

export const loginToAmazonFlex = async (email, password) => {
  try {
    const response = await fetch(`http://192.168.1.221:8080/api/flex/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, password })
    });
    const data = await response.json();
    return { success: true, data };
  } catch (error) {
    return { success: false, error: error.message };
  }
};

export const grabBlocks = async () => {
  try {
    const response = await fetch(`http://192.168.1.221:8080/api/flex/blocks`);
    const data = await response.json();
    return { success: true, data };
  } catch (error) {
    return { success: false, error: error.message };
  }
};
