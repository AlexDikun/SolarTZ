document.addEventListener('DOMContentLoaded', function() {
    const adsGrid = document.getElementById('adsGrid');
    const adCards = adsGrid.querySelectorAll('.ad-card');

    for (let i = 0; i < 3; i++ ) {
        adCards.forEach(card => {
            const clone = card.cloneNode(true);
            adsGrid.appendChild(clone)
        });
    }
});