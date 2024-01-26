document.getElementById('profile-icon').addEventListener('click', function() {
    document.getElementById('profile-image-input').click();
});

document.getElementById('add-payment-btn').addEventListener('click', function() {
    var paymentOptions = document.getElementById('payment-options');
    paymentOptions.style.display = 'block';
});

document.getElementById('add-credit-card-btn').addEventListener('click', function() {
    document.getElementById('credit-card-input').style.display = 'block';
    document.getElementById('bank-account-input').style.display = 'none';
});

document.getElementById('add-bank-account-btn').addEventListener('click', function() {
    document.getElementById('credit-card-input').style.display = 'none';
    document.getElementById('bank-account-input').style.display = 'block';
});

document.getElementById('confirm-credit-card-btn').addEventListener('click', function() {
    var creditCardNumber = document.getElementById('credit-card').value;
    // Ajoutez ici la logique pour traiter le numéro de carte de crédit
    console.log('Numéro de Carte de Crédit: ', creditCardNumber);
});

document.getElementById('confirm-bank-account-btn').addEventListener('click', function() {
    var bankAccountNumber = document.getElementById('bank-account').value;
    // Ajoutez ici la logique pour traiter le numéro de compte bancaire
    console.log('Numéro de Compte: ', bankAccountNumber);
});

  


