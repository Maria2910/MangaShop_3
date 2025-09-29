// Базовый URL для API
const API_BASE = '/admin/mangas';

// Функция для получения CSRF токена
function getCsrfToken() {
    const token = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

    if (!token) {
        console.warn('CSRF token not found');
        return null;
    }

    return {
        token: token,
        headerName: header || 'X-CSRF-TOKEN'
    };
}

// Универсальная функция для AJAX запросов с улучшенной обработкой ошибок
async function makeRequest(url, method, data = null) {
    const csrf = getCsrfToken();

    const config = {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        }
    };

    // Добавляем CSRF токен если есть
    if (csrf) {
        config.headers[csrf.headerName] = csrf.token;
    }

    // Добавляем тело запроса если нужно
    if (data) {
        config.body = JSON.stringify(data);
    }

    console.log(`🔄 Making ${method} request to: ${url}`, data);

    try {
        const response = await fetch(url, config);
        console.log(`📨 Response status: ${response.status} ${response.statusText}`);

        // Проверяем Content-Type
        const contentType = response.headers.get('content-type');
        console.log(`📄 Content-Type: ${contentType}`);

        if (!response.ok) {
            let errorText;
            try {
                const errorData = await response.json();
                errorText = errorData.message || JSON.stringify(errorData);
            } catch (e) {
                errorText = await response.text();
            }
            throw new Error(`HTTP ${response.status}: ${errorText}`);
        }

        // Пытаемся распарсить JSON
        if (contentType && contentType.includes('application/json')) {
            const result = await response.json();
            console.log('✅ JSON response:', result);
            return result;
        } else {
            const text = await response.text();
            console.log('📝 Text response:', text);
            // Если ответ не JSON, но статус 200, считаем успешным
            return { success: true, message: 'Operation completed' };
        }

    } catch (error) {
        console.error('❌ Request failed:', error);
        throw error;
    }
}

// Функция для показа уведомлений
function showNotification(message, type = 'success') {
    // Создаем контейнер если его нет
    let container = document.getElementById('ajax-notifications');
    if (!container) {
        container = document.createElement('div');
        container.id = 'ajax-notifications';
        container.className = 'ajax-notifications';
        document.body.appendChild(container);
    }

    const notification = document.createElement('div');
    notification.className = `admin-alert ${type}`;
    notification.innerHTML = `
        <span>${message}</span>
        <button type="button" class="admin-alert-close">&times;</button>
    `;

    container.appendChild(notification);

    // Автоудаление через 5 секунд
    setTimeout(() => {
        if (notification.parentElement) {
            notification.remove();
        }
    }, 5000);

    // Закрытие по клику
    notification.querySelector('.admin-alert-close').addEventListener('click', () => {
        notification.remove();
    });
}

// Обновление цены
async function updatePrice(mangaId) {
    const priceInput = document.getElementById(`price-${mangaId}`);
    const price = parseFloat(priceInput.value);
    const saveButton = priceInput.nextElementSibling;

    if (isNaN(price) || price < 0) {
        showNotification('Введите корректную цену', 'error');
        priceInput.focus();
        return;
    }

    // Показываем индикатор загрузки
    const originalText = saveButton.innerHTML;
    saveButton.innerHTML = '⏳';
    saveButton.disabled = true;

    try {
        console.log(`💰 Updating price for manga ${mangaId} to ${price}`);

        const result = await makeRequest(
            `${API_BASE}/${mangaId}/update-price`,
            'POST',
            { price: price }
        );

        console.log('💰 Price update result:', result);

        if (result && result.success) {
            showNotification('✅ Цена успешно обновлена');
        } else {
            const errorMessage = result?.message || 'Неизвестная ошибка';
            showNotification('❌ ' + errorMessage, 'error');
        }
    } catch (error) {
        console.error('❌ Ошибка обновления цены:', error);
        showNotification('❌ Ошибка обновления цены: ' + error.message, 'error');
    } finally {
        // Восстанавливаем кнопку
        saveButton.innerHTML = originalText;
        saveButton.disabled = false;
    }
}

// Обновление количества на складе
async function updateStock(mangaId) {
    const stockInput = document.getElementById(`stock-${mangaId}`);
    const stock = parseInt(stockInput.value);
    const saveButton = stockInput.nextElementSibling;

    if (isNaN(stock) || stock < 0) {
        showNotification('Введите корректное количество', 'error');
        stockInput.focus();
        return;
    }

    // Показываем индикатор загрузки
    const originalText = saveButton.innerHTML;
    saveButton.innerHTML = '⏳';
    saveButton.disabled = true;

    try {
        console.log(`📦 Updating stock for manga ${mangaId} to ${stock}`);

        const result = await makeRequest(
            `${API_BASE}/${mangaId}/update-stock`,
            'POST',
            { stockQuantity: stock }
        );

        console.log('📦 Stock update result:', result);

        if (result && result.success) {
            showNotification('✅ Количество успешно обновлено');
        } else {
            const errorMessage = result?.message || 'Неизвестная ошибка';
            showNotification('❌ ' + errorMessage, 'error');
        }
    } catch (error) {
        console.error('❌ Ошибка обновления количества:', error);
        showNotification('❌ Ошибка обновления количества: ' + error.message, 'error');
    } finally {
        // Восстанавливаем кнопку
        saveButton.innerHTML = originalText;
        saveButton.disabled = false;
    }
}

// Удаление манги
async function deleteManga(mangaId, button) {
    if (!confirm('Вы уверены, что хотите удалить эту мангу?')) {
        return;
    }

    const row = button.closest('tr');

    try {
        console.log(`🗑️ Deleting manga ${mangaId}`);

        const result = await makeRequest(
            `${API_BASE}/${mangaId}/delete`,
            'POST'
        );

        console.log('🗑️ Delete result:', result);

        if (result && result.success) {
            showNotification('🗑️ Манга успешно удалена');
            // Плавно скрываем строку
            row.style.opacity = '0';
            row.style.transition = 'opacity 0.3s ease';
            setTimeout(() => {
                row.remove();
                // Если таблица пустая, показываем сообщение
                if (document.querySelectorAll('.admin-table tbody tr').length === 0) {
                    showEmptyState();
                }
            }, 300);
        } else {
            const errorMessage = result?.message || 'Неизвестная ошибка';
            showNotification('❌ ' + errorMessage, 'error');
        }
    } catch (error) {
        console.error('❌ Ошибка удаления:', error);
        showNotification('❌ Ошибка удаления: ' + error.message, 'error');
    }
}

// Показ пустого состояния
function showEmptyState() {
    const tableBody = document.querySelector('.admin-table tbody');
    if (tableBody && tableBody.children.length === 0) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="6" class="admin-empty">
                    <i class="fas fa-book-open"></i>
                    <h3>Манга не найдена</h3>
                    <p>Добавьте первую мангу в каталог</p>
                </td>
            </tr>
        `;
    }
}

// Инициализация после загрузки DOM
document.addEventListener('DOMContentLoaded', function() {
    console.log('🔄 Admin JS loaded with enhanced error handling');

    // Обработка нажатия Enter в полях ввода
    document.querySelectorAll('.price-input, .stock-input').forEach(input => {
        input.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                const mangaId = this.id.split('-')[1];
                if (this.classList.contains('price-input')) {
                    updatePrice(mangaId);
                } else {
                    updateStock(mangaId);
                }
            }
        });
    });

    // Автофокус и выделение текста в инпутах
    document.querySelectorAll('.admin-input').forEach(input => {
        input.addEventListener('click', function() {
            this.select();
        });
    });

    // Закрытие статических алертов
    document.querySelectorAll('.admin-alert-close').forEach(button => {
        button.addEventListener('click', function() {
            this.closest('.admin-alert').style.display = 'none';
        });
    });

    // Тестовый вызов для проверки
    console.log('✅ Admin JS initialized successfully');
});