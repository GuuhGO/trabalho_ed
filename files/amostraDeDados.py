import faker

fake = faker.Faker("pt_br")

for _ in range(100):
    cpf = fake.cpf().replace(".", "").replace("-", "")
    tipo = fake.random_choices(["Físico", "Jurídico"], 1)[0]
    nome = fake.random_choices([fake.name(), fake.company()], 1)[0]
    logra = fake.street_address()
    num = fake.random_number(3)
    comp = fake.random_letter()
    cep = fake.postcode()
    telefone = fake.service_phone_number()
    email = fake.email()
    csv = f"{cpf};{tipo};{nome};{logra};{num};{comp};{cep};{telefone};{email}"
    print(csv)
