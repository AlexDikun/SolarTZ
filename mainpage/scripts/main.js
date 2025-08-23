document.addEventListener('DOMContentLoaded', function() {
    const adsGrid = document.getElementById('adsGrid');
    const ads = adsGrid.innerHTML; 
    adsGrid.innerHTML = ads.repeat(4);
});