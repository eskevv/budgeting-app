import {toast} from "react-toastify";

export const waait = () =>
  new Promise((res) => setTimeout(res, Math.random() * 800));

// colors
const generateRandomColor = () => {
  const existingBudgetLength = fetchData("budgets")?.length ?? 0;
  return `${existingBudgetLength * 34} 65% 50%`;
};

// Local storage
export const fetchData = (key) => {
  return JSON.parse(localStorage.getItem(key));
};

export async function fetchBudgets () {
  console.log("fetching")
  try {
    let res = await fetch('http://localhost:8080/api/budgets')
    return await res.json()
  } catch (e) {
    throw new Error("There was a problem creating your account.");
  }
}


export async function fetchExpenses () {
  console.log("fetching expenses")
  try {
    let res = await fetch('http://localhost:8080/api/expenses')
    return await res.json()
  } catch (e) {
    throw new Error("There was a problem creating your account.");
  }
}

// Get all items from local storage
export const getAllMatchingItems = ({category, key, value}) => {
  const data = fetchData(category) ?? [];
  return data.filter((item) => item[key] === value);
};

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
export async function createBudget ({name, amount}) {
  const newItem = {
    name: name,
    createdAt: Date.now(),
    amount: +amount,
    color: generateRandomColor(),
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

// total spent by budget
export const calculateSpentByBudget = (budgetId) => {
  const expenses = fetchData("expenses") ?? [];
  const budgetSpent = expenses.reduce((acc, expense) => {
    // check if expense.id === budgetId I passed in
    if ( expense.budgetId !== budgetId ) return acc;

    // add the current amount to my total
    return (acc += expense.amount);
  }, 0);
  return budgetSpent;
};

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
