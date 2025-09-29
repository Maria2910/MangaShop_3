document.addEventListener('DOMContentLoaded', function() {
    const editBtn = document.getElementById('editProfileBtn');
    const cancelBtn = document.getElementById('cancelEditBtn');
    const viewMode = document.getElementById('viewMode');
    const editMode = document.getElementById('editMode');
    const alerts = document.querySelectorAll('.alert');

    // Скрываем сообщения при редактировании
    function hideAlerts() {
        alerts.forEach(alert => {
            alert.classList.add('hidden');
        });
    }

    // Показываем сообщения при отмене
    function showAlerts() {
        alerts.forEach(alert => {
            alert.classList.remove('hidden');
        });
    }

    // Переключение в режим редактирования
    editBtn.addEventListener('click', function() {
        hideAlerts();
        viewMode.style.opacity = '0';
        viewMode.style.transition = 'opacity 0.3s ease';

        setTimeout(() => {
            viewMode.style.display = 'none';
            editMode.style.display = 'block';

            // Принудительный reflow для анимации
            void editMode.offsetWidth;

            editMode.style.opacity = '1';
            editMode.style.transition = 'opacity 0.3s ease';

            editBtn.style.display = 'none';
        }, 300);
    });

    // Отмена редактирования
    cancelBtn.addEventListener('click', function() {
        editMode.style.opacity = '0';
        editMode.style.transition = 'opacity 0.3s ease';

        setTimeout(() => {
            editMode.style.display = 'none';
            viewMode.style.display = 'grid'; // Важно: grid вместо block

            // Принудительный reflow для анимации
            void viewMode.offsetWidth;

            viewMode.style.opacity = '1';
            viewMode.style.transition = 'opacity 0.3s ease';

            editBtn.style.display = 'flex';
            showAlerts();
        }, 300);
    });

    // Инициализация
    viewMode.style.opacity = '1';
    viewMode.style.transition = 'opacity 0.3s ease';
    viewMode.style.display = 'grid'; // Фиксируем grid layout

    editMode.style.opacity = '0';
    editMode.style.transition = 'opacity 0.3s ease';
});