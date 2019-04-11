@SortingOfLocationsDisplayed            @Platform
Feature: Locations should be sorted in Alphanumeric order
As a user I want to verify if list of locations are sorted in Alphanumeric order

@AlphaNumericSortingOfLocations			@Automatable
Scenario:  Verify if order in which locations are displayed when tapped on location drop down
Given user launches and logs in to the Lyric Application
When user creates the following locations:
| Location Name     |
| Home              |
| wld               |
| Wld1              |
| WLD2              |
| wld 3             |
| 12345             |
| WLD A             |
| S2                |
| S1                |
| S3                |
| S4                |
| S23               |
| S 21              |
And when user taps on location drop down in Dashboard screen
Then list of locations should be sorted in the following alphanumeric order:
| Location Name     |
| 12345             |
| Home              |
| S 21              |
| S1                |
| S2                |
| S23               |
| S3                |
| S4                |
| wld               |
| wld 3             |
| WLD A             |
| Wld1              |
| WLD2              |


@AlphaNumericSortingOfLocationsBySelectingAnyLocation			@Automatable
Scenario:  Verify if locations are sorted by alphanumeric order when user selects any location from location drop down
Given user launches and logs in to the Lyric Application
When user creates the following locations:
| Location Name     |
| Home              |
| wld               |
| Wld1              |
| WLD2              |
| wld 3             |
| 12345             |
| WLD A             |
| S2                |
| S1                |
| S3                |
| S4                |
| S23               |
| S 21              |
And when user taps on location drop down in Dashboard screen
Then list of locations should be sorted in the following alphanumeric order:
| Location Name     |
| 12345             |
| Home              |
| S 21              |
| S1                |
| S2                |
| S23               |
| S3                |
| S4                |
| wld               |
| wld 3             |
| WLD A             |
| Wld1              |
| WLD2              |
When user selects the following location from location drop down:
| Location Name     |
| wld               |
Then user should be displayed with that locations dashboard.
And when user taps on location drop down in Dashboard screen
Then list of locations should be sorted in the following alphanumeric order:
| Location Name     |
| 12345             |
| Home              |
| S 21              |
| S1                |
| S2                |
| S23               |
| S3                |
| S4                |
| wld 3             |
| WLD A             |
| Wld1              |
| WLD2              |


@AlphaNumericSortingOfLocationsByDeletingExistingLocation			@Automatable
Scenario:  Verify if locations are sorted by alphanumeric order when user deletes any location
Given user launches and logs in to the Lyric Application
When user creates the following locations:
| Location Name     |
| Home              |
| wld               |
| Wld1              |
| WLD2              |
| wld 3             |
| 12345             |
| WLD A             |
| S2                |
| S1                |
| S3                |
| S4                |
| S23               |
| S 21              |
And when user taps on location drop down in Dashboard screen
Then list of locations should be sorted in the following alphanumeric order:
| Location Name     |
| 12345             |
| Home              |
| S 21              |
| S1                |
| S2                |
| S23               |
| S3                |
| S4                |
| wld               |
| wld 3             |
| WLD A             |
| Wld1              |
| WLD2              |
When user selects the following location from location drop down:
| Location Name     |
| S2                |
Then user should be displayed with that locations dashboard.
When user navigates to "Address" screen from the "Dashboard" screen
Then user should be displayed with the "Address" screen
When user "deletes location" by clicking on "Delete Location" button
Then user should receive a "Delete Location" popup
When user "Clicks on NO in" the "Delete Location" popup
Then user should be displayed with the "Address" screen
When user "deletes location" by clicking on "Delete Location" button
Then user should receive a "Delete Location" popup
When user "Clicks on YES in" the "Delete Location" popup
And user should be displayed with the "Default location Dashboard" screen
When user taps on location drop down in Dashboard screen
Then list of locations should be sorted in the following alphanumeric order:
| Location Name     |
| 12345             |
| Home              |
| S 21              |
| S1                |
| S2                |
| S23               |
| S3                |
| S4                |
| wld 3             |
| WLD A             |
| Wld1              |
| WLD2              |