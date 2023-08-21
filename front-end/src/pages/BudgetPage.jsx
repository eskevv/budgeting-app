// rrd imports
import {useLoaderData} from "react-router-dom";

// library
import {toast} from "react-toastify";

// components
import AddExpenseForm from "../components/AddExpenseForm";
import BudgetItem from "../components/BudgetItem";
import Table from "../components/Table";

// helpers
import {calculateBudgetExpenses, createExpense, deleteItem} from "../helpers";

// loader
export async function budgetLoader({params}) {
  const budget = await (await fetch(`http://localhost:8080/api/budgets/${params.id}`)).json()
  const allExpenses = await (await fetch(`http://localhost:8080/api/expenses`)).json()
  const expenses = allExpenses.filter(x => parseInt(x.budget.id) === parseInt(params.id))
  const budgetSpent = await calculateBudgetExpenses()

  if ( !budget ) {
    throw new Error("The budget you’re trying to find doesn’t exist");
  }

  return {budget, expenses, budgetSpent};
}

// action
export async function budgetAction({request}) {
  const data = await request.formData();
  const {_action, ...values} = Object.fromEntries(data);

  if ( _action === "createExpense" ) {
    try {
      createExpense({
        name: values.newExpense,
        amount: values.newExpenseAmount,
        budgetId: values.newExpenseBudget,
      });
      return toast.success(`Expense ${values.newExpense} created!`);
    } catch (e) {
      throw new Error("There was a problem creating your expense.");
    }
  }

  if ( _action === "deleteExpense" ) {
    try {
      deleteItem({
        key: "expenses",
        id: values.expenseId,
      });
      return toast.success("Expense deleted!");
    } catch (e) {
      throw new Error("There was a problem deleting your expense.");
    }
  }
}

const BudgetPage = () => {
  const {budget, expenses, budgetSpent} = useLoaderData();

  return (
    <div className="grid-lg" style={{"--accent": budget.color,}} >
      <h1 className="h2">
        <span className="accent">{budget.name}</span> Overview
      </h1>
      <div className="flex-lg">
        <BudgetItem budget={budget} showDelete={true} spent={budgetSpent[budget.id]}/>
        <AddExpenseForm budgets={[budget]}/>
      </div>
      {expenses && expenses.length > 0 && (
        <div className="grid-md">
          <h2>
            <span className="accent">{budget.name}</span> Expenses
          </h2>
          <Table expenses={expenses} showBudget={false}/>
        </div>
      )}
    </div>
  );
};
export default BudgetPage;
