-- Insert roles
INSERT INTO public.roles (name) VALUES ('USER'), ('ADMIN');

-- Insert categories (types of doctors)
INSERT INTO public.categories (type) VALUES
                                         ('Veterinarian'),
                                         ('Surgeon'),
                                         ('Dentist'),
                                         ('Ophthalmologist'),
                                         ('Dermatologist'),
                                         ('Radiologist');

-- Insert service types for each category
INSERT INTO public.service_types (category_id, name) VALUES
-- Veterinarian services
(1, 'General Checkup'),
(1, 'Vaccination'),
(1, 'Emergency Care'),
-- Surgeon services
(2, 'Spay/Neuter'),
(2, 'Orthopedic Surgery'),
(2, 'Soft Tissue Surgery'),
-- Dentist services
(3, 'Dental Cleaning'),
(3, 'Tooth Extraction'),
(3, 'Oral Surgery'),
-- Ophthalmologist services
(4, 'Eye Examination'),
(4, 'Cataract Surgery'),
(4, 'Glaucoma Treatment'),
-- Dermatologist services
(5, 'Skin Allergy Test'),
(5, 'Dermatological Surgery'),
(5, 'Skin Disease Treatment'),
-- Radiologist services
(6, 'X-Ray'),
(6, 'Ultrasound'),
(6, 'CT Scan');

-- Insert users with encrypted passwords (using the BCRYPT hash function)
INSERT INTO public.users (address, email, first_name, last_name, password, phone) VALUES
                                                                                      ('123 Elm St', 'user1@example.com', 'John', 'Doe', '$2a$10$cnhyfYr4SSm0WQB8fXeHKevUaq9A/6nTkIrYqwQTrfEflbG3tXJqm', '123-456-7890'),  -- 123456 encrypted
                                                                                      ('456 Oak St', 'user2@example.com', 'Jane', 'Doe', '$2a$10$cnhyfYr4SSm0WQB8fXeHKevUaq9A/6nTkIrYqwQTrfEflbG3tXJqm', '234-567-8901'),  -- 123456 encrypted
                                                                                      ('789 Pine St', 'user3@example.com', 'Alice', 'Smith', '$2a$10$cnhyfYr4SSm0WQB8fXeHKevUaq9A/6nTkIrYqwQTrfEflbG3tXJqm', '345-678-9012'),  -- 123456 encrypted
                                                                                      ('101 Maple St', 'employee1@example.com', 'Bob', 'Johnson', '$2a$10$cnhyfYr4SSm0WQB8fXeHKevUaq9A/6nTkIrYqwQTrfEflbG3tXJqm', '456-789-0123'),  -- 123456 encrypted
                                                                                      ('202 Birch St', 'employee2@example.com', 'Carol', 'Williams', '$2a$10$cnhyfYr4SSm0WQB8fXeHKevUaq9A/6nTkIrYqwQTrfEflbG3tXJqm', '567-890-1234'),  -- 123456 encrypted
                                                                                      ('303 Cedar St', 'admin@example.com', 'Admin', 'User', '$2a$10$cnhyfYr4SSm0WQB8fXeHKevUaq9A/6nTkIrYqwQTrfEflbG3tXJqm', '678-901-2345');  -- 123456 encrypted

-- Assign roles to users
-- Regular users
INSERT INTO public.users_roles (role_id, user_id) VALUES
                                                      (1, 1),  -- USER role to John Doe
                                                      (1, 2),  -- USER role to Jane Doe
                                                      (1, 3);  -- USER role to Alice Smith

-- Employees
INSERT INTO public.users_roles (role_id, user_id) VALUES
                                                      (1, 4),  -- USER role to Bob Johnson
                                                      (1, 5);  -- USER role to Carol Williams

-- Admin
INSERT INTO public.users_roles (role_id, user_id) VALUES
    (2, 6);  -- ADMIN role to Admin User

-- Insert employees
INSERT INTO public.employees (user_id) VALUES
                                           (4),  -- Bob Johnson
                                           (5);  -- Carol Williams

-- Assign categories to employees
INSERT INTO public.employee_categories (category_id, employee_id) VALUES
                                                                      (1, 4),  -- Veterinarian category to Bob Johnson
                                                                      (2, 5);  -- Surgeon category to Carol Williams

-- Insert a single schedule for one employee (Bob Johnson)
INSERT INTO public.schedules (day_start_time, day_end_time, time_slot_duration, employee_id) VALUES
    ('08:00:00', '17:00:00', 3600000000000, 4);

-- Insert visitors
INSERT INTO public.visitors (user_id) VALUES
                                          (1),  -- John Doe
                                          (2),  -- Jane Doe
                                          (3);  -- Alice Smith

-- Insert pets for users
INSERT INTO public.pets (animal_type, date_of_birth, name, owner_id) VALUES
                                                                         ('Dog', '2020-01-01', 'Rex', 1),  -- John Doe's dog
                                                                         ('Cat', '2019-02-02', 'Whiskers', 1),  -- John Doe's cat
                                                                         ('Bird', '2021-03-03', 'Tweety', 2),  -- Jane Doe's bird
                                                                         ('Dog', '2018-04-04', 'Buddy', 2),  -- Jane Doe's dog
                                                                         ('Cat', '2020-05-05', 'Shadow', 3),  -- Alice Smith's cat
                                                                         ('Fish', '2022-06-06', 'Goldie', 3);  -- Alice Smith's fish


UPDATE public.service_types SET description = 'Routine health examination for pets to ensure overall wellness.' WHERE id = 1;
UPDATE public.service_types SET description = 'Administering vaccines to pets to prevent common diseases.' WHERE id = 2;
UPDATE public.service_types SET description = 'Immediate medical attention for pets in critical conditions.' WHERE id = 3;
UPDATE public.service_types SET description = 'Surgical procedure to sterilize pets and prevent breeding.' WHERE id = 4;
UPDATE public.service_types SET description = 'Surgical treatment for bone and joint issues in pets.' WHERE id = 5;
UPDATE public.service_types SET description = 'Surgical procedures involving non-bony tissues like muscles and organs.' WHERE id = 6;
UPDATE public.service_types SET description = 'Professional cleaning of pets teeth to prevent dental diseases.' WHERE id = 7;
UPDATE public.service_types SET description = 'Removal of damaged or infected teeth from pets.' WHERE id = 8;
UPDATE public.service_types SET description = 'Surgical treatment for oral health issues in pets.' WHERE id = 9;
UPDATE public.service_types SET description = 'Comprehensive examination of pets eyes to detect issues.' WHERE id = 10;
UPDATE public.service_types SET description = 'Surgical removal of cataracts from pets eyes.' WHERE id = 11;
UPDATE public.service_types SET description = 'Medical or surgical treatment to manage glaucoma in pets.' WHERE id = 12;
UPDATE public.service_types SET description = 'Testing pets for allergic reactions to various substances.' WHERE id = 13;
UPDATE public.service_types SET description = 'Surgical treatment for skin-related issues in pets.' WHERE id = 14;
UPDATE public.service_types SET description = 'Treatment for various skin diseases affecting pets.' WHERE id = 15;
UPDATE public.service_types SET description = 'Radiographic imaging to diagnose internal issues in pets.' WHERE id = 16;
UPDATE public.service_types SET description = 'Sonographic imaging to visualize internal organs of pets.' WHERE id = 17;
UPDATE public.service_types SET description = 'Detailed cross-sectional imaging for diagnosing complex conditions in pets.' WHERE id = 18;
