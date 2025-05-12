// admin.js

// Завантаження списку автобусів
function loadBuses() {
    axios.get('/api/buses')
        .then(response => {
            const buses = response.data;
            const tableBody = document.querySelector("#bus-table");
            tableBody.innerHTML = "";  // Очистка таблиці

            buses.forEach(bus => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${bus.name}</td>
                    <td>${bus.capacity}</td>
                    <td>${bus.route}</td>
                    <td>${new Date(bus.departureTime).toLocaleString()}</td>
                    <td>
                        <a href="/admin/buses/edit/${bus.id}" class="bg-yellow-500 text-white px-4 py-2 rounded-md">Edit</a>
                        <a href="/admin/buses/delete/${bus.id}" class="bg-red-500 text-white px-4 py-2 rounded-md">Delete</a>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error loading buses:', error);
        });
}

window.onload = loadBuses;
