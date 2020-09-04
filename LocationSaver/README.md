Please note, this is not a finished project. In the future I will add some extra features like 
doing data permanent and make listview content editable so the user can name the place as he/she wants.

# Updated:

Data is already permanent using shared preferences.
The user can now edit the list as he wants, deleting entries, or editing the address.

# How it works: 

the user can add new places by tapping on "add new place", to add the place, just long tap where he wants.
the location is automatically saved. 

If the user wants to edit or delete an entry, just need to long tap in the entry he wants.
A dialog will be shown where he can edit or delete the address.

There is a "delete list" button that i dont think is useful, but it was a good learning on how to
delete the sotred data.

# Updated:

Due to having some problems with LocationManager and LocationListener to get user's location
(worked well in the emulator, but not in a real device), I've decided to look for another way
to get it. So now user's location obtained using FusedLocationProviderClient. 
At least in my device, it isnt really accurate, but works.