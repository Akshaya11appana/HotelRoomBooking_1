INSERT INTO room_types (code, name, description, base_price, max_occupancy, archived)
VALUES 
 ('DLX', 'Deluxe Room', 'City view, 28 sqm, workspace', 4500.00, 3, FALSE),
 ('STU', 'Studio', 'Compact studio with kitchenette', 3800.00, 2, FALSE);

INSERT INTO room_type_amenities (room_type_id, amenity)
SELECT id, 'WiFi' FROM room_types WHERE code IN ('DLX','STU');
INSERT INTO room_type_amenities (room_type_id, amenity)
SELECT id, 'AC' FROM room_types WHERE code IN ('DLX','STU');
INSERT INTO room_type_amenities (room_type_id, amenity)
SELECT id, 'Breakfast' FROM room_types WHERE code='DLX';
INSERT INTO room_type_amenities (room_type_id, amenity)
SELECT id, 'Kitchenette' FROM room_types WHERE code='STU';
