// Script de conversão do schema SQL para MongoDB (AppFinanceiro)
// MongoBD não possui tabelas nem foreign keys; usamos collections e referências manuais.

use("AppFinanceiro");

db.department.drop();
db.seller.drop();

// CREATE TABLE department
const computers = ObjectId();
const electronics = ObjectId();
const fashion = ObjectId();
const books = ObjectId();

db.department.insertMany([
  { _id: computers, Name: "Computers" },
  { _id: electronics, Name: "Electronics" },
  { _id: fashion, Name: "Fashion" },
  { _id: books, Name: "Books" },
]);

// CREATE TABLE seller (DepartmentId referencia department._id, como a FOREIGN KEY original)
db.seller.insertMany([
  { _id: ObjectId(), Name: "Bob Brown", Email: "bob@gmail.com", BirthDate: new Date("1998-04-21T00:00:00Z"), BaseSalary: 1000, DepartmentId: computers },
  { _id: ObjectId(), Name: "Maria Green", Email: "maria@gmail.com", BirthDate: new Date("1979-12-31T00:00:00Z"), BaseSalary: 3500, DepartmentId: electronics },
  { _id: ObjectId(), Name: "Alex Grey", Email: "alex@gmail.com", BirthDate: new Date("1988-01-15T00:00:00Z"), BaseSalary: 2200, DepartmentId: computers },
  { _id: ObjectId(), Name: "Martha Red", Email: "martha@gmail.com", BirthDate: new Date("1993-11-30T00:00:00Z"), BaseSalary: 3000, DepartmentId: books },
  { _id: ObjectId(), Name: "Donald Blue", Email: "donald@gmail.com", BirthDate: new Date("2000-01-09T00:00:00Z"), BaseSalary: 4000, DepartmentId: fashion },
  { _id: ObjectId(), Name: "Alex Pink", Email: "bob@gmail.com", BirthDate: new Date("1997-03-04T00:00:00Z"), BaseSalary: 3000, DepartmentId: electronics },
]);

print("Banco AppFinanceiro criado com sucesso!");
print("Departments: " + db.department.countDocuments());
print("Sellers: " + db.seller.countDocuments());
