document.addEventListener("DOMContentLoaded", () => {
  fetch("/api/products")
    .then(res => res.json())
    .then(data => {
      const tbody = document.querySelector("#productTable tbody");
      tbody.innerHTML = "";
      data.forEach(product => {
        const row = `<tr>
          <td class="border px-4 py-2">${product.name}</td>
          <td class="border px-4 py-2">${product.description}</td>
          <td class="border px-4 py-2">${product.price}</td>
        </tr>`;
        tbody.innerHTML += row;
      });
    });

  document.getElementById("productForm")?.addEventListener("submit", async (e) => {
    e.preventDefault();
    const product = {
      name: document.getElementById("name").value,
      description: document.getElementById("description").value,
      price: parseFloat(document.getElementById("price").value)
    };

    const res = await fetch("/api/products", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(product)
    });

    if (res.ok) {
      alert("Product added!");
      location.reload();
    } else {
      alert("Failed to add product");
    }
  });
});