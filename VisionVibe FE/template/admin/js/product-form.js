// Color mapping for display names
const colorNames = {
    // Frame colors
    black: 'Đen',
    brown: 'Nâu',
    silver: 'Bạc',
    gold: 'Vàng',
    red: 'Đỏ',
    blue: 'Xanh dương',
    green: 'Xanh lá',
    
    // Lens colors
    clear: 'Trong suốt',
    gray: 'Xám',
    yellow: 'Vàng',
    mirror: 'Gương'
};

// Store selected colors
let selectedFrameColors = new Set();
let selectedLensColors = new Set();

// Function to create a color tag
function createColorTag(color, container) {
    const tag = document.createElement('div');
    tag.className = 'color-tag';
    tag.innerHTML = `
        ${colorNames[color]}
        <span class="remove-tag" data-color="${color}">&times;</span>
    `;
    
    // Add remove functionality
    tag.querySelector('.remove-tag').addEventListener('click', () => {
        tag.remove();
        if (container.id === 'frameColorTags') {
            selectedFrameColors.delete(color);
        } else {
            selectedLensColors.delete(color);
        }
    });
    
    return tag;
}

// Function to handle color selection
function handleColorSelection(select, container, selectedColors) {
    select.addEventListener('change', (e) => {
        const selectedColor = e.target.value;
        
        if (selectedColor && !selectedColors.has(selectedColor)) {
            selectedColors.add(selectedColor);
            container.appendChild(createColorTag(selectedColor, container));
            select.value = ''; // Reset select to placeholder
        }
    });
}

// Initialize color selection handlers
document.addEventListener('DOMContentLoaded', () => {
    const frameColorSelect = document.getElementById('frameColorSelect');
    const lensColorSelect = document.getElementById('lensColorSelect');
    const frameColorTags = document.getElementById('frameColorTags');
    const lensColorTags = document.getElementById('lensColorTags');
    
    handleColorSelection(frameColorSelect, frameColorTags, selectedFrameColors);
    handleColorSelection(lensColorSelect, lensColorTags, selectedLensColors);
    
    // Handle form submission
    document.getElementById('productForm').addEventListener('submit', (e) => {
        e.preventDefault();
        
        // Convert selected colors to arrays for form submission
        const formData = new FormData(e.target);
        formData.append('frameColors', Array.from(selectedFrameColors));
        formData.append('lensColors', Array.from(selectedLensColors));
        
        // Here you would typically send the formData to your server
        console.log('Form submitted with colors:', {
            frameColors: Array.from(selectedFrameColors),
            lensColors: Array.from(selectedLensColors)
        });
        
        // Redirect back to products page
        window.location.href = 'products.html';
    });
}); 