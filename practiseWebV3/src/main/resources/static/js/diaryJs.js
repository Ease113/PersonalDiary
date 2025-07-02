   async function populateDropdowns() {
   	try {
       	// Fetch categories
	   	const userProfileName = document.getElementById('user_profile_name').textContent;
	   	const categoriesResponse = await fetch('/Diary/Write/getCategories?userName=' + userProfileName);
       	const categories = await categoriesResponse.json();
       	const categorySelect = document.getElementById('entryCategory');
       	categorySelect.innerHTML = '<option value="">Select a Category</option>'; // Clear existing options
       	categories.forEach(cat => { // Recreating categories option structure
           	const option = document.createElement('option');
           	option.value = cat.name; 
           	option.textContent = cat.name;
           	categorySelect.appendChild(option);
       	});

       	// Populate category list in modal
       	const categoryListUl = document.getElementById('categoryList');
       	categoryListUl.innerHTML = ''; // Clear existing
       	categories.forEach(cat => {
           	const td = document.createElement('tr');
           	td.setAttribute('data-id', cat.id); 
           	td.innerHTML = `<td>${cat.name}</td>
   							<td>${cat.counts}</td>
                           	<td><div class="modal-list-item-actions">
                           	<button type="button" class="btn-danger" onclick="deleteCategory(${cat.id}, ${cat.counts}, this)">Delete</button>
                           	</div></td>`;
           	categoryListUl.appendChild(td);
       	});

       	// Fetch series
       	const seriesResponse = await fetch('/Diary/Write/getSeries?userName=' + userProfileName);
       	const series = await seriesResponse.json();
       	const seriesSelect = document.getElementById('entrySeries');
       	seriesSelect.innerHTML = '<option value="">Select a Series</option>'; // Clear existing options
       	series.forEach(ser => {
           	const option = document.createElement('option');
           	option.value = ser.name; 
           	option.textContent = ser.name;
           	seriesSelect.appendChild(option);
       	});

       	// Populate series list in modal
       	const seriesListUl = document.getElementById('seriesList');
       	seriesListUl.innerHTML = ''; // Clear existing
       	series.forEach(ser => {
           	const td = document.createElement('tr');
           	td.setAttribute('data-id', ser.id); 
           	td.innerHTML = `<td>${ser.name}</td>
	   						<td>${ser.counts}</td>
                           	<td><div class="modal-list-item-actions">
                           	<button type="button" class="btn-danger" onclick="deleteSeries(${ser.id}, ${ser.counts}, this)">Delete</button>
                           	</div></td>`;
	       	seriesListUl.appendChild(td);
	       });

   	} catch (error) {
   		console.error('Error fetching categories or series:', error);
   		// Optionally, show an alert or error message to the user
		}
   	}

   // Call populateDropdowns on page load
   document.addEventListener('DOMContentLoaded', populateDropdowns);


   // --- Functions for Categories Edit Modal ---
   function openEditCategoriesModal() {
   	// Re-populate the list in the modal every time it opens to ensure it's fresh
  	populateDropdowns();
   	document.getElementById('editCategoriesModal').classList.add('show');
   }

   function closeEditCategoriesModal() {
  	 document.getElementById('editCategoriesModal').classList.remove('show');
   }

   async function addCategory() {
	const userProfileName = document.getElementById('user_profile_name').textContent;
   	const newCategoryName = document.getElementById('newCategoryName').value.trim();
   	if (newCategoryName) {
       	try {
           	const response = await fetch('/Diary/Write/setCategories', {
           		method: 'POST',
               	headers: {
                   	'Content-Type': 'application/json'
               	},
               	body: JSON.stringify({ category: newCategoryName, userName : userProfileName }) // Send new category name
           	});
           	if (response.ok) {
               	const addedCategory = await response.json(); 
               	document.getElementById('newCategoryName').value = ''; // Clear input
               	await populateDropdowns(); // Re-fetch and update all dropdowns/lists
               	console.log('Added category:', addedCategory);
           	} else {
               	console.error('Failed to add category:', response.statusText);
           	}
       	} catch (error) {
           	console.error('Error adding category:', error);
       	}
   	}
   	}

   async function deleteCategory(categoryId, counts, buttonElement) {
	
	   if (counts !== 0) {
		alert("Before delete a category\nMake it sure there are no entries that use this category")
		return false;
	   }
	   if (!confirm('Are you sure you want to delete this category?')) { // Using confirm for simplicity, consider custom modal
           return;
       }
       try {
			const response = await fetch('/Diary/Write/delCategories', {
               method: 'POST',
               headers: {
                   'Content-Type': 'application/json'
               },
               body: JSON.stringify({ reg_id : categoryId }) // Send new category name
           //const response = await fetch(`/Diary/Write/delCategories/${categoryId}`, { // Replace with your actual API endpoint
               //method: 'DELETE'
           });
           if (response.ok) {
               buttonElement.closest('td').remove(); // Remove from modal list immediately
               await populateDropdowns(); // Re-fetch and update the main dropdown
               console.log('Deleted category with ID:', categoryId);
           } else {
               console.error('Failed to delete category:', response.statusText);
           }
       } catch (error) {
           console.error('Error deleting category:', error);
       }
   }

   async function saveCategories() {
       // In this setup, 'add' and 'delete' already interact with the server.
       // 'Save Changes' here would only be needed if you had bulk edits or reordering.
       // For simple add/delete, the changes are immediate.
       // If you need a "save all" feature, you'd collect all items from #categoryList
       // and send them as an array to a backend endpoint.
   	console.log('Save Categories clicked (individual adds/deletes are instant)');
   	closeEditCategoriesModal();
   	}


   // --- Functions for Series Edit Modal ---
   function openEditSeriesModal() {
   	// Re-populate the list in the modal every time it opens to ensure it's fresh
  	 populateDropdowns();
   	document.getElementById('editSeriesModal').classList.add('show');
   }

   function closeEditSeriesModal() {
   	document.getElementById('editSeriesModal').classList.remove('show');
   }

   async function addSeries() {
	const userProfileName = document.getElementById('user_profile_name').textContent;
   	const newSeriesName = document.getElementById('newSeriesName').value.trim();
   	if (newSeriesName) {
       	try {
   			const response = await fetch('/Diary/Write/setSeries', { // Replace with your actual API endpoint
           		method: 'POST',
               	headers: {
                   	'Content-Type': 'application/json'
               	},
               	body: JSON.stringify({ series : newSeriesName, userName : userProfileName }) // Send new series name
           	});
           	if (response.ok) {
               	const addedSeries = await response.json();
               	document.getElementById('newSeriesName').value = ''; // Clear input
               	await populateDropdowns(); // Re-fetch and update all dropdowns/lists
               	console.log('Added series:', addedSeries);
           	} else {
               	console.error('Failed to add series:', response.statusText);
           	}
       	} catch (error) {
           	console.error('Error adding series:', error);
       	}
   	}
   	}

   async function deleteSeries(seriesId, counts, buttonElement) {
	if (counts !== 0) {
		alert("Before delete a series\nMake it sure there are no entries that use this series")
		return false;
	}
   	if (!confirm('Are you sure you want to delete this series?')) { // Using confirm for simplicity
       	return;
   	}
   	try {
		const response = await fetch('/Diary/Write/delSeries', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
			},
			body: JSON.stringify({reg_id: seriesId})
		});
       	if (response.ok) {
           	buttonElement.closest('td').remove(); // Remove from modal list immediately
           	await populateDropdowns(); // Re-fetch and update the main dropdown
           	console.log('Deleted series with ID:', seriesId);
       	} else {
           	console.error('Failed to delete series:', response.statusText);
       	}
   	} catch (error) {
       	console.error('Error deleting series:', error);
   	}
}

   async function saveSeries() {
       console.log('Save Series clicked (individual adds/deletes are instant)');
       closeEditSeriesModal();
   }