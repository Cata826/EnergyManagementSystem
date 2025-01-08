

// import { useState, useEffect, useRef } from 'react';
// import axios from 'axios';
// import Chart from 'chart.js/auto'; 

// const EnergyConsumptionChart = () => {
//   const [chartData, setChartData] = useState([]);
//   const [loading, setLoading] = useState(true);
//   const [selectedDate, setSelectedDate] = useState('');
//   const chartRef = useRef(null); 

//   useEffect(() => {
//     const fetchData = async () => {
//       const personId = localStorage.getItem('id'); 
//       if (!personId || !selectedDate) return;

//       try {
//         const response = await axios.get(`http://localhost:8082/broker/energy-consumption/${personId}?date=${selectedDate}`);
//         // const response = await axios.get(`http://monitoring.localhost/broker/energy-consumption/${personId}?date=${selectedDate}`);
//         console.log('Fetched data:', response.data); 
//         setChartData(response.data);
//       } catch (error) {
//         console.error('Failed to fetch energy data:', error);
//       }
//       setLoading(false);
//     };

//     fetchData();
//   }, [selectedDate]); 

//   const prepareChartData = () => {
//     const labels = chartData.map(item => `${item.hour}:00`);
//     const data = chartData.map(item => item.totalConsumption);
//     return { labels, data };
//   };

//   const createChart = () => {
//       if (chartRef.current) {
//       chartRef.current.destroy(); 
//     }

//     const ctx = document.getElementById('energyChart').getContext('2d');
//     const { labels, data } = prepareChartData();

//     chartRef.current = new Chart(ctx, {
//       type: 'line', 
//       data: {
//         labels: labels,
//         datasets: [{
//           label: 'Total Energy Consumption (kWh)',
//           data: data,
//           fill: false,
//           borderColor: 'rgb(75, 192, 192)',
//           tension: 0.1
//         }]
//       },
//       options: {
//         scales: {
//           x: {
//             title: {
//               display: true,
//               text: 'Hour of Day'
//             }
//           },
//           y: {
//             title: {
//               display: true,
//               text: 'Total Energy Consumption (kWh)'
//             },
//             beginAtZero: true
//           }
//         }
//       }
//     });
//   };

//   useEffect(() => {
//     if (chartData.length > 0) {
//       createChart(); 
//     }
//   }, [chartData]); 

//   return (
//     <div>
//       <input 
//         type="date" 
//         value={selectedDate} 
//         onChange={e => setSelectedDate(e.target.value)} 
//         placeholder="Select a date"
//       />
//       <canvas id="energyChart"></canvas>
//       {loading && <p>Loading data...</p>}
//     </div>
//   );
// };

// export default EnergyConsumptionChart;
import { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import Chart from 'chart.js/auto'; 

const EnergyConsumptionChart = () => {
  const [chartData, setChartData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedDate, setSelectedDate] = useState('');
  const chartRef = useRef(null); 

  useEffect(() => {
    const fetchData = async () => {
      const personId = localStorage.getItem('id'); 
      if (!personId || !selectedDate) return;

      // Get token from localStorage
      const token = localStorage.getItem('token');

      try {
        // const response = await axios.get(`http://localhost:8082/broker/energy-consumption/${personId}?date=${selectedDate}`, {
        //   headers: {
        //     'Authorization': `Bearer ${token}`,  // Add the token to the header
        //     'Content-Type': 'application/json',  // Ensure Content-Type is set
        //   }
        // });
        const response = await axios.get(`http://monitoring.localhost/broker/energy-consumption/${personId}?date=${selectedDate}`, {
          headers: {
            'Authorization': `Bearer ${token}`,  // Add the token to the header
            'Content-Type': 'application/json',  // Ensure Content-Type is set
          }
        });
        console.log('Fetched data:', response.data); 
        setChartData(response.data);
      } catch (error) {
        console.error('Failed to fetch energy data:', error);
      }
      setLoading(false);
    };

    fetchData();
  }, [selectedDate]); 

  const prepareChartData = () => {
    const labels = chartData.map(item => `${item.hour}:00`);
    const data = chartData.map(item => item.totalConsumption);
    return { labels, data };
  };

  const createChart = () => {
      if (chartRef.current) {
      chartRef.current.destroy(); 
    }

    const ctx = document.getElementById('energyChart').getContext('2d');
    const { labels, data } = prepareChartData();

    chartRef.current = new Chart(ctx, {
      type: 'line', 
      data: {
        labels: labels,
        datasets: [{
          label: 'Total Energy Consumption (kWh)',
          data: data,
          fill: false,
          borderColor: 'rgb(75, 192, 192)',
          tension: 0.1
        }]
      },
      options: {
        scales: {
          x: {
            title: {
              display: true,
              text: 'Hour of Day'
            }
          },
          y: {
            title: {
              display: true,
              text: 'Total Energy Consumption (kWh)'
            },
            beginAtZero: true
          }
        }
      }
    });
  };

  useEffect(() => {
    if (chartData.length > 0) {
      createChart(); 
    }
  }, [chartData]); 

  return (
    <div>
      <input 
        type="date" 
        value={selectedDate} 
        onChange={e => setSelectedDate(e.target.value)} 
        placeholder="Select a date"
      />
      <canvas id="energyChart"></canvas>
      {loading && <p>Loading data...</p>}
    </div>
  );
};

export default EnergyConsumptionChart;
