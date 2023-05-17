import React from "react";

function FilterButtons({ fields, onSelect }) {
    const handleCheckboxChange = (event) => {
        const selectedValue = event.target.value;
        if (event.target.checked) {
            onSelect((prevSelected) => [...prevSelected, selectedValue]);
        } else {
            onSelect((prevSelected) =>
                prevSelected.filter((value) => value !== selectedValue)
            );
        }
    };

    return (
        <div>
            <h3>Filter by Field:</h3>
            {fields.map((field) => (
                <div key={field.id}>
                    <label>
                        <input
                            type="checkbox"
                            value={field.name}
                            onChange={handleCheckboxChange}
                        />
                        {field.name}
                    </label>
                </div>
            ))}
        </div>
    );
}

export default FilterButtons;
