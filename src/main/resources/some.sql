--
-- Create tables.
--

CREATE TABLE world.city
(
    id          SERIAL PRIMARY KEY,
    name        text         NOT NULL,
    countrycode character(3) NOT NULL,
    district    text         NOT NULL,
    population  integer      NOT NULL
);

CREATE TABLE world.country
(
    code           character(3) PRIMARY KEY,
    name           text         NOT NULL,
    continent      text         NOT NULL,
    region         text         NOT NULL,
    surfacearea    real         NOT NULL,
    indepyear      smallint,
    population     integer      NOT NULL,
    lifeexpectancy real,
    gnp            numeric(10, 2),
    gnpold         numeric(10, 2),
    localname      text         NOT NULL,
    governmentform text         NOT NULL,
    headofstate    text,
    capital        integer,
    code2          character(2) NOT NULL,
    CONSTRAINT country_continent_check CHECK ((
            ((((((continent = 'Asia'::text) OR (continent = 'Europe'::text)) OR (continent = 'North America'::text)) OR
               (continent = 'Africa'::text)) OR (continent = 'Oceania'::text)) OR (continent = 'Antarctica'::text)) OR
            (continent = 'South America'::text)))
);

CREATE TABLE world.countrylanguage
(
    countrycode character(3) NOT NULL,
    language    varchar(255) NOT NULL,
    isofficial  boolean      NOT NULL,
    percentage  real         NOT NULL,
    PRIMARY KEY (countrycode, language)
);

--
-- Add foreign key constraints.
--

ALTER TABLE world.country
    ADD CONSTRAINT country_capital_fkey FOREIGN KEY (capital) REFERENCES world.city (id);

ALTER TABLE world.countrylanguage
    ADD CONSTRAINT countrylanguage_countrycode_fkey FOREIGN KEY (countrycode) REFERENCES world.country (code);

--
-- Consulta
--

SELECT c.code,
       c.name,
       c.continent,
       c.region,
       c.surfacearea    surface_area,
       c.indepyear      indep_year,
       c.population,
       c.lifeexpectancy life_expectancy,
       c.gnp,
       c.localname      local_name,
       c.governmentform government_form,
       c.headofstate    head_of_state,
       c.code2,
       c.capital,
       cy.name          capital_name
FROM world.country c
         LEFT OUTER JOIN world.city cy ON cy.id = c.capital
WHERE 1 = 1
  AND (LOWER(c.name)
    LIKE CONCAT('%', LOWER('Bol'), '%'))
    LIMIT 2 OFFSET 1;