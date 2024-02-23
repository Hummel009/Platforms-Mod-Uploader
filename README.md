[![Code Smells][code_smells_badge]][code_smells_link]
[![Maintainability Rating][maintainability_rating_badge]][maintainability_rating_link]
[![Security Rating][security_rating_badge]][security_rating_link]
[![Bugs][bugs_badge]][bugs_link]
[![Vulnerabilities][vulnerabilities_badge]][vulnerabilities_link]
[![Duplicated Lines (%)][duplicated_lines_density_badge]][duplicated_lines_density_link]
[![Reliability Rating][reliability_rating_badge]][reliability_rating_link]
[![Quality Gate Status][quality_gate_status_badge]][quality_gate_status_link]
[![Technical Debt][technical_debt_badge]][technical_debt_link]
[![Lines of Code][lines_of_code_badge]][lines_of_code_link]

Программа для загрузки множества версий мода на CurseForge. Исходя из имени файла, определяетв версию Minecraft
и тип загрузчика (Forge, Fabric...), после чего загружает с нужными данными на платформу.

## Использование

В среде программирования, прямо внутри папки проекта, создать папку "folders". В ней создать несколько папок,
в качестве названия использующих айдишники модов на CurseForge. В каждой из них можно положить любое количество 
файлов вида `[1.3.2] API Upload Test 24.02.29 (Forge).jar`, где в квадратных скобках версия игры, а в круглых - 
загрузчик.

После этого остаётся сгенерировать токен CurseForge (как это сделать, можно узнать, загуглив 
`CurseForge Ulpoad API` и воспользоваться им при запуске программы. Очевидно, что в коде программы тоже
нужно отредактировать айдишники модов, которые используются в forEach.

<!----------------------------------------------------------------------------->

[code_smells_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_CurseForge-Mod-Uploader&metric=code_smells

[code_smells_link]: https://sonarcloud.io/summary/overall?id=Hummel009_CurseForge-Mod-Uploader

[maintainability_rating_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_CurseForge-Mod-Uploader&metric=sqale_rating

[maintainability_rating_link]: https://sonarcloud.io/summary/overall?id=Hummel009_CurseForge-Mod-Uploader

[security_rating_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_CurseForge-Mod-Uploader&metric=security_rating

[security_rating_link]: https://sonarcloud.io/summary/overall?id=Hummel009_CurseForge-Mod-Uploader

[bugs_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_CurseForge-Mod-Uploader&metric=bugs

[bugs_link]: https://sonarcloud.io/summary/overall?id=Hummel009_CurseForge-Mod-Uploader

[vulnerabilities_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_CurseForge-Mod-Uploader&metric=vulnerabilities

[vulnerabilities_link]: https://sonarcloud.io/summary/overall?id=Hummel009_CurseForge-Mod-Uploader

[duplicated_lines_density_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_CurseForge-Mod-Uploader&metric=duplicated_lines_density

[duplicated_lines_density_link]: https://sonarcloud.io/summary/overall?id=Hummel009_CurseForge-Mod-Uploader

[reliability_rating_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_CurseForge-Mod-Uploader&metric=reliability_rating

[reliability_rating_link]: https://sonarcloud.io/summary/overall?id=Hummel009_CurseForge-Mod-Uploader

[quality_gate_status_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_CurseForge-Mod-Uploader&metric=alert_status

[quality_gate_status_link]: https://sonarcloud.io/summary/overall?id=Hummel009_CurseForge-Mod-Uploader

[technical_debt_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_CurseForge-Mod-Uploader&metric=sqale_index

[technical_debt_link]: https://sonarcloud.io/summary/overall?id=Hummel009_CurseForge-Mod-Uploader

[lines_of_code_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_CurseForge-Mod-Uploader&metric=ncloc

[lines_of_code_link]: https://sonarcloud.io/summary/overall?id=Hummel009_CurseForge-Mod-Uploader
