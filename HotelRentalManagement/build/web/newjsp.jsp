<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Loft Section</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
      font-family: 'Segoe UI', sans-serif;
    }
    .timeline-container {
      background: #fff;
      padding: 20px;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.05);
      overflow-x: auto;
      max-width: 1000px;
      margin: auto;
    }
    .timeline-table {
      width: 100%;
      border-collapse: collapse;
    }
    .timeline-table th, .timeline-table td {
      border: 1px solid #e0e0e0;
      padding: 12px 8px;
      text-align: left;
      white-space: nowrap;
    }
    .room-category {
      background-color: #e9ecef;
      font-weight: bold;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }
    .room-category:hover {
      background-color: #d6d8db;
    }
    .room-label {
      font-weight: 500;
    }
    .timeline-row td {
      height: 50px;
      transition: all 0.3s ease;
    }
    .toggle-icon {
      display: inline-block;
      width: 1.5rem;
      transition: transform 0.3s ease;
    }
  </style>
</head>
<body class="p-4">

  <div class="timeline-container">
    <table class="timeline-table">
      <thead>
        <tr>
          <th>Rooms</th>
          <th>Fr 30</th>
          <th>Sa 1</th>
          <th>Su 2</th>
          <th>Mo 3</th>
        </tr>
      </thead>
      <tbody>
        <!-- Loft Category -->
        <tr class="room-category" data-bs-toggle="collapse" data-bs-target=".loft-rows" aria-expanded="true">
          <td colspan="5"><span class="toggle-icon">?</span> Loft</td>
        </tr>
        <tr class="timeline-row collapse show loft-rows">
          <td class="room-label">L1</td><td colspan="4"></td>
        </tr>
        <tr class="timeline-row collapse show loft-rows">
          <td class="room-label">L2</td><td colspan="4"></td>
        </tr>
      </tbody>
    </table>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    document.querySelectorAll(".room-category").forEach(header => {
      header.addEventListener("click", () => {
        const icon = header.querySelector(".toggle-icon");
        const targetClass = header.getAttribute("data-bs-target").substring(1);
        const rows = document.querySelectorAll("." + targetClass);
        const isVisible = rows[0].classList.contains("show");

        rows.forEach(row => {
          row.classList.toggle("show");
        });

        icon.textContent = isVisible ? "?" : "?";
      });
    });
  </script>

</body>
</html>
