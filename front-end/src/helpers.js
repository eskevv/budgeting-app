export const waait = () =>
  new Promise((res) => setTimeout(res, Math.random() * 800));

// colors
export async function generateRandomColor () {
  const res = await fetch('http://localhost:8080/api/budgets')
  const budgets = await res.json()
  const existingBudgetLength = budgets?.length ?? 0;
  return `${existingBudgetLength * 34} 65% 50%`;
};

// Local storage
export const fetchData = (key) => {
  return JSON.parse(localStorage.getItem(key));
};

export async function fetchBudgets() {
  try {
    let res = await fetch('http://localhost:8080/api/budgets')
    return await res.json()
  } catch (e) {
    throw new Error("There was a problem creating your account.");
  }
}

export async function fetchExpenses() {
  try {
    let res = await fetch('http://localhost:8080/api/expenses')
    return await res.json()
  } catch (e) {
    throw new Error("There was a problem creating your account.");
  }
}

// delete item from local storage
export const deleteItem = ({key, id}) => {
  const existingData = fetchData(key);
  if ( id ) {
    const newData = existingData.filter((item) => item.id !== id);
    return localStorage.setItem(key, JSON.stringify(newData));
  }
  return localStorage.removeItem(key);
};

// create budget
export async function createBudget({name, amount}) {
  const newItem = {
    name: name,
    createdAt: Date.now(),
    amount: +amount,
    color: await generateRandomColor(),
  };
  const r = await fetch("http://localhost:8080/api/budgets", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(newItem),
  });
}

// create expense
export async function createExpense({name, amount, budgetId}) {
  const newItem = {
    expenseName: name,
    createdAt: Date.now(),
    amount: +amount,
    budgetId: budgetId,
  };
  await fetch("http://localhost:8080/api/expenses", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(newItem),
  });
}

export async function calculateBudgetExpenses() {
  let spendings = {}
  const expenses = await fetchExpenses()
  for (let expense of expenses) {
    if (!spendings.hasOwnProperty(expense.budget.id)) {
      spendings[expense.budget.id] = 0;
    }
    spendings[expense.budget.id] += +expense.amount
  }
  return spendings
}

// total spent by budget
export async function calculateSpentByBudget(budgetId) {
  const expense = await fetchExpenses()
  const budgetSpent = expenses.reduce((acc, expense) => {
    // check if expense.id === budgetId I passed in
    if ( expense.budgetId !== budgetId ) return acc;

    // add the current amount to my total
    return (acc += expense.amount);
  }, 0);
  return budgetSpent;
}

// FORMATTING
export const formatDateToLocaleString = (epoch) =>
  new Date(epoch).toLocaleDateString();

// Formatting percentages
export const formatPercentage = (amt) => {
  return amt.toLocaleString(undefined, {
    style: "percent",
    minimumFractionDigits: 0,
  });
};

// Format currency
export const formatCurrency = (amt) => {
  return amt.toLocaleString(undefined, {
    style: "currency",
    currency: "USD",
  });
};
