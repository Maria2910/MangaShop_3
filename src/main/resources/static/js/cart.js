function updateQuantity(button, change) {
    const form = button.closest('.quantity-form');
    const input = form.querySelector('.quantity-input');
    let quantity = parseInt(input.value) || 1;

    quantity += change;

    const max = parseInt(input.getAttribute('max')) || Infinity;
    const min = parseInt(input.getAttribute('min')) || 1;

    if (quantity >= min && quantity <= max) {
        input.value = quantity;
        form.submit();
    }
}

// Обработчики для модального окна
document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('orderModal');
    const checkoutBtn = document.querySelector('.checkout-btn');
    const closeBtn = document.querySelector('.close');

    if (checkoutBtn && modal) {
        checkoutBtn.addEventListener('click', function() {
            modal.style.display = 'block';
        });
    }

    if (closeBtn && modal) {
        closeBtn.addEventListener('click', function() {
            modal.style.display = 'none';
        });
    }

    window.addEventListener('click', function(event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    });

    // Валидация формы
    const orderForm = document.querySelector('.order-form');
    if (orderForm) {
        orderForm.addEventListener('submit', function(e) {
            const inputs = this.querySelectorAll('input[required], select[required], textarea[required]');
            let valid = true;

            inputs.forEach(input => {
                if (!input.value.trim()) {
                    input.style.borderColor = '#e53e3e';
                    valid = false;
                } else {
                    input.style.borderColor = '';
                }
            });

            if (!valid) {
                e.preventDefault();
                alert('Пожалуйста, заполните все обязательные поля');
            }
        });
    }
});

document.addEventListener('DOMContentLoaded', function() {
    // Обработка null значений в форме
    const customerNameInput = document.getElementById('customerName');
    if (customerNameInput && customerNameInput.value.includes('null')) {
        customerNameInput.value = '';
        customerNameInput.placeholder = 'Введите ваше ФИО';
        customerNameInput.style.borderColor = '#fed7d7';
        customerNameInput.style.backgroundColor = '#fff5f5';
    }

    // Очистка других полей с null
    const inputs = document.querySelectorAll('input[value*="null"]');
    inputs.forEach(input => {
        if (input.value.includes('null')) {
            input.value = '';
            input.style.borderColor = '#fed7d7';
            input.style.backgroundColor = '#fff5f5';
        }
    });

    // Восстановление нормального стиля при фокусе
    const formInputs = document.querySelectorAll('.form-group input, .form-group textarea');
    formInputs.forEach(input => {
        input.addEventListener('focus', function() {
            this.style.borderColor = '#667eea';
            this.style.backgroundColor = 'white';
        });

        input.addEventListener('blur', function() {
            if (this.value === '') {
                this.style.borderColor = '#fed7d7';
                this.style.backgroundColor = '#fff5f5';
            } else {
                this.style.borderColor = '#e9ecef';
                this.style.backgroundColor = 'white';
            }
        });
    });
});