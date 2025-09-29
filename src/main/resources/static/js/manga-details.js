document.addEventListener('DOMContentLoaded', function() {
    // Функция для отображения звезд в отзывах
    function renderStars() {
        document.querySelectorAll('.stars[data-rating]').forEach(stars => {
            const rating = parseFloat(stars.getAttribute('data-rating'));
            const fullStars = Math.floor(rating);

            let starsHtml = '';

            for (let i = 0; i < fullStars; i++) {
                starsHtml += '<span class="filled">★</span>';
            }

            for (let i = fullStars; i < 5; i++) {
                starsHtml += '<span>☆</span>';
            }

            stars.innerHTML = starsHtml;
            stars.title = rating.toFixed(1);
        });
    }

    // Просто делаем звезды кликабельными
    document.querySelectorAll('.star-rating label').forEach(label => {
        label.addEventListener('click', function() {
            const input = document.getElementById(this.htmlFor);
            if (input) {
                input.checked = true;
            }
        });
    });

    // Запускаем отображение звезд
    renderStars();
});